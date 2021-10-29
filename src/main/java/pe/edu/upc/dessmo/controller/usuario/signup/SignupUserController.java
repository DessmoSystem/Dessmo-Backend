package pe.edu.upc.dessmo.controller.usuario.signup;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.dessmo.controller.util.multiuse_code.Code_SetUserRol;
import pe.edu.upc.dessmo.controller.util.multiuse_code.Code_SignupValidations;
import pe.edu.upc.dessmo.controller.util.multiuse_code.Code_UploadFoto;
import pe.edu.upc.dessmo.dto.request.usuario.general.UtilityTokenRequest;
import pe.edu.upc.dessmo.dto.request.usuario.signup.SignupUserRequest;
import pe.edu.upc.dessmo.dto.response.general.MessageResponse;
import pe.edu.upc.dessmo.enums.RolNombre;
import pe.edu.upc.dessmo.model.Rol;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.model.UtilityToken;
import pe.edu.upc.dessmo.service.IImagenService;
import pe.edu.upc.dessmo.service.IRolService;
import pe.edu.upc.dessmo.service.IUsuarioService;
import pe.edu.upc.dessmo.service.IUtilityTokenService;
import pe.edu.upc.dessmo.tools.UtilityDessmo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SignupUserController {

    final
    PasswordEncoder passwordEncoder;

    final
    IUsuarioService usuarioService;

    final
    IRolService rolService;

    final
    IImagenService imagenService;

    final
    JavaMailSender mailSender;

    final
    IUtilityTokenService utilityTokenService;

    Code_SignupValidations code_signupValidations;

    @Value("${front.baseurl}")
    private String baseurl;

    public SignupUserController(PasswordEncoder passwordEncoder, IUsuarioService usuarioService, IRolService rolService,
                                IImagenService imagenService, JavaMailSender mailSender,
                                IUtilityTokenService utilityTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
        this.rolService = rolService;
        this.imagenService = imagenService;
        this.mailSender = mailSender;
        this.utilityTokenService = utilityTokenService;
    }

    @PostMapping("/usuario/signup")
    public ResponseEntity<?> SignupUsuario(@RequestBody SignupUserRequest signupUserRequest,
                                           HttpServletRequest request) {

        Optional<Usuario> usuario_data = usuarioService.BuscarUsuario_By_EmailUsuario(signupUserRequest.getEmailUsuario());

        if (usuario_data.isPresent()) {
            return Code_SignupValidations.SignupValidationResponse(usuario_data);
        } else {
            Optional<Rol> rol_data = rolService.BuscarRol_Nombre(RolNombre.ROLE_USER);

            if (rol_data.isPresent()) {
                try {
                    if (usuarioService.ValidarUsername(signupUserRequest.getUsernameUsuario())) {
                        return new ResponseEntity<>(new MessageResponse("El Usuario ya se encuentra en uso."),
                                HttpStatus.CONFLICT);
                    } else {
                        Usuario usuario =
                                new Usuario(
                                        signupUserRequest.getNombreUsuario(),
                                        signupUserRequest.getApellidoUsuario(),
                                        signupUserRequest.getEmailUsuario(),
                                        signupUserRequest.getUsernameUsuario(),
                                        passwordEncoder.encode(signupUserRequest.getPasswordUsuario())
                                );

                        usuarioService.GuardarUsuario(usuario);

                        Optional<Usuario> user_data =
                                usuarioService.BuscarUsuario_By_EmailUsuario(signupUserRequest.getEmailUsuario());

                        if (user_data.isPresent()) {
                            Usuario user = user_data.get();

                            //Asignando Rol: User
                            Code_SetUserRol.SetUserRol(user, rol_data);

                            //Asignando Fecha de Registro Actual
                            user.setFecharegistroUsuario(LocalDate.now());

                            //Asignando Estado de Cuenta: PENDIENTE
                            user.setEstadoUsuario("PENDIENTE");

                            //Asignando Foto por Defecto: User
                            InputStream fotoStream = getClass().getResourceAsStream("/static/img/UserUser.png");
                            Code_UploadFoto.AssignFoto(user, fotoStream, imagenService);

                            String token = RandomString.make(50);

                            //Generando Token: Verificación
                            UtilityToken utilityToken = new UtilityToken(
                                    token,
                                    "Signup Usuario Verify",
                                    LocalDateTime.now().plusHours(48),
                                    user
                            );
                            utilityTokenService.GuardarUtilityToken(utilityToken);

                            String url = UtilityDessmo.GenerarUrl(request) + "/api/usuario_verify_gateway?token=" + token;

                            EnviarCorreo(signupUserRequest.getEmailUsuario(), url);

                            usuarioService.GuardarUsuario(user);

                            return new ResponseEntity<>(new MessageResponse("Se ha registrado satisfactoriamente su " +
                                    "solicitud. Revise su bandeja de entrada para verificar su cuenta, recuerde que " +
                                    "dispone no más de 48 horas para culminar dicho proceso y sea aprobado posteriormente. " +
                                    "De lo contrario, deberá rellenar el formulario nuevamente."),
                                    HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(new MessageResponse("Ocurrió un error durante el proceso " +
                                    "de registro de datos."),
                                    HttpStatus.NOT_FOUND);
                        }
                    }
                } catch (Exception e) {
                    return new ResponseEntity<>(new MessageResponse("Ocurrió un error al asignar la foto de perfil " +
                            "por defecto." + e),
                            HttpStatus.EXPECTATION_FAILED);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse("Ocurrió un error al otorgar sus permisos " +
                        "correspondientes."),
                        HttpStatus.NOT_FOUND);
            }
        }
    }

    @GetMapping("/usuario_verify_gateway")
    void RedirectUserVerify(HttpServletResponse response, @Param(value = "token") String token) throws IOException {

        Optional<UtilityToken> utilitytoken_data = utilityTokenService.BuscarUtilityToken_By_Token(token);

        if (utilitytoken_data.isPresent()) {
            response.sendRedirect(baseurl + "/signup/usuario/verify/" + token);
        } else {
            response.sendRedirect(baseurl + "/error/403");
        }
    }

    @PutMapping("/usuario/signup/verify")
    public ResponseEntity<?> SignupUsuarioVerify(@RequestBody UtilityTokenRequest utilityTokenRequest) {

        Optional<UtilityToken> utilitytoken_data = utilityTokenService.BuscarUtilityToken_By_Token(utilityTokenRequest.getUtilityToken());

        if (utilitytoken_data.isPresent()) {
            UtilityToken utilitytoken = utilitytoken_data.get();

            Optional<Usuario> usuario_data = usuarioService.BuscarUsuario_By_UtilityToken(utilitytoken.getIdUtilityToken());

            if (usuario_data.isPresent()) {
                Usuario usuario = usuario_data.get();

                usuario.setEstadoUsuario("VERIFICADO");
                usuarioService.GuardarUsuario(usuario);

                utilityTokenService.EliminarUtilityToken(utilitytoken.getIdUtilityToken());

                return new ResponseEntity<>(new MessageResponse("Se ha verificado el usuario satisfactoriamente."),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse("Ocurrió un error durante la búsqueda del usuario."),
                        HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("Ocurrió un error en el proceso de verificación."),
                    HttpStatus.NOT_FOUND);
        }
    }

    private void EnviarCorreo(String email, String url) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("fenosys.assistant@gmail.com", "Dessmo Support");
        helper.setTo(email);

        String asunto = "Verificación de Cuenta";

        String contenido =
                "<h2>Hola,</h1>" +
                        "<p>Gracias por registrarte en Fenosys.</p>" +
                        "<br>Haz click en el link que se encuentra debajo para verificar su cuenta y tener acceso al sistema." +
                        "<a href=" + url + ">Verificar Mi Cuenta</a>";

        helper.setSubject(asunto);
        helper.setText(contenido, true);

        mailSender.send(message);
    }
}
