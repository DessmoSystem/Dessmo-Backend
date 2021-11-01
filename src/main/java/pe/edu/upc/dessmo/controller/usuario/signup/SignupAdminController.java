package pe.edu.upc.dessmo.controller.usuario.signup;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pe.edu.upc.dessmo.controller.util.multiuse_code.Code_SetUserRol;
import pe.edu.upc.dessmo.controller.util.multiuse_code.Code_SignupValidations;
import pe.edu.upc.dessmo.controller.util.multiuse_code.Code_UploadFoto;
import pe.edu.upc.dessmo.dto.request.usuario.general.EmailRequest;
import pe.edu.upc.dessmo.dto.request.usuario.signup.SignupAdminRequest;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SignupAdminController {

    final
    IUsuarioService usuarioService;

    final
    IUtilityTokenService utilityTokenService;

    final
    JavaMailSender mailSender;

    final
    IRolService rolService;

    final
    PasswordEncoder passwordEncoder;

    final
    IImagenService imagenService;

    final
    TemplateEngine templateEngine;

    @Value("${front.baseurl}")
    private String baseurl;

    @Value("${image.logo.url}")
    private String img_logo;

    @Value("${image.check.url}")
    private String img_check;

    public SignupAdminController(IUsuarioService usuarioService, IUtilityTokenService utilityTokenService,
                                 JavaMailSender mailSender, IRolService rolService,
                                 PasswordEncoder passwordEncoder, IImagenService imagenService,
                                 TemplateEngine templateEngine) {
        this.usuarioService = usuarioService;
        this.utilityTokenService = utilityTokenService;
        this.mailSender = mailSender;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
        this.imagenService = imagenService;
        this.templateEngine = templateEngine;
    }

    @PostMapping("/admin/signup_request")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> SignupAdminRequest(@RequestBody EmailRequest emailRequest,
                                                HttpServletRequest request) {

        Optional<Usuario> usuario_data = usuarioService.BuscarUsuario_By_EmailUsuario(emailRequest.getEmailUsuario());

        if (usuario_data.isPresent()) {
            return Code_SignupValidations.SignupValidationResponse(usuario_data);
        } else {
            String token = RandomString.make(50);

            Usuario new_admin = new Usuario();
            new_admin.setEmailUsuario(emailRequest.getEmailUsuario());
            new_admin.setEstadoUsuario("PENDIENTE");
            usuarioService.GuardarUsuario(new_admin);

            Optional<Usuario> admin_data = usuarioService.BuscarUsuario_By_EmailUsuario(emailRequest.getEmailUsuario());

            if (admin_data.isPresent()) {
                try {
                    Usuario admin = admin_data.get();

                    String identity1_admin = RandomString.make(2);
                    String identity2_admin = RandomString.make(3);
                    String username = "admin_" + identity1_admin + admin.getIdUsuario() + identity2_admin;

                    admin.setUsernameUsuario(username);
                    usuarioService.GuardarUsuario(admin);

                    UtilityToken utilityToken = new UtilityToken(
                            token,
                            "Signup Admin",
                            LocalDateTime.now().plusHours(72),
                            admin
                    );
                    utilityTokenService.GuardarUtilityToken(utilityToken);

                    String url = UtilityDessmo.GenerarUrl(request) + "/api/admin_register_gateway?token=" + token;

                    EnviarCorreo(emailRequest.getEmailUsuario(), url);
                } catch (UnsupportedEncodingException e) {
                    return new ResponseEntity<>(new MessageResponse("Error: " + e), HttpStatus.BAD_REQUEST);
                } catch (MessagingException e) {
                    return new ResponseEntity<>(new MessageResponse("Error al enviar el correo."),
                            HttpStatus.BAD_REQUEST);
                }

                return new ResponseEntity<>(new MessageResponse("Se envió el correo a la bandeja de entrada del " +
                        "Solicitante de manera satisfactoria."), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse("Ocurrió un error en la solicitud de Registro."),
                        HttpStatus.NOT_FOUND);
            }
        }
    }

    @GetMapping("/admin_register_gateway")
    void RedirectSignupAdminRequest(HttpServletResponse response, @Param(value = "token") String token) throws IOException {

        Optional<UtilityToken> utilitytoken_data = utilityTokenService.BuscarUtilityToken_By_Token(token);

        if (utilitytoken_data.isPresent()) {
            response.sendRedirect(baseurl + "/signup/admin/" + token);
        } else {
            response.sendRedirect(baseurl);
        }
    }

    @PutMapping("/admin/signup")
    public ResponseEntity<?> SignupAdminProcess(@RequestBody SignupAdminRequest signupAdminRequest) throws IOException {

        Optional<UtilityToken> utilitytoken_data =
                utilityTokenService.BuscarUtilityToken_By_Token(signupAdminRequest.getUtilitytokenUsuario());

        if (utilitytoken_data.isPresent()) {
            UtilityToken utilitytoken = utilitytoken_data.get();

            Optional<Usuario> admin_data = usuarioService.BuscarUsuario_By_IDUtilityToken(utilitytoken.getIdUtilityToken());

            if (admin_data.isPresent()) {
                Optional<Rol> rol_data = rolService.BuscarRol_Nombre(RolNombre.ROLE_ADMIN);

                if (rol_data.isPresent()) {
                    Usuario admin = admin_data.get();

                    admin.setNombreUsuario(signupAdminRequest.getNombreUsuario());
                    admin.setApellidoUsuario(signupAdminRequest.getApellidoUsuario());
                    admin.setPasswordUsuario(passwordEncoder.encode(signupAdminRequest.getPasswordUsuario()));

                    //Asignando Rol: Administrador
                    Code_SetUserRol.SetUserRol(admin, rol_data);

                    //Asignando Fecha de Registro Actual
                    admin.setFecharegistroUsuario(LocalDate.now());

                    //Cambiando Estado de Cuenta a ACTIVO
                    admin.setEstadoUsuario("ACTIVO");

                    //Asignando Foto por Defecto: Agricultor
                    InputStream fotoStream = getClass().getResourceAsStream("/static/img/AdminUser.png");
                    Code_UploadFoto.AssignFoto(admin, fotoStream, imagenService);

                    utilityTokenService.EliminarUtilityToken(utilitytoken.getIdUtilityToken());
                    usuarioService.GuardarUsuario(admin);

                    return new ResponseEntity<>(new MessageResponse("Se ha registrado satisfactoriamente."),
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new MessageResponse("Ocurrió un error al otorgar roles."),
                            HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse("Ocurrió un error durante el proceso de Registro."),
                        HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("El proceso de registro ya no se encuentra disponible."),
                    HttpStatus.NOT_FOUND);
        }
    }

    private void EnviarCorreo(String email, String url) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("dessmosystem@gmail.com", "Dessmo Support");
        helper.setTo(email);

        String asunto = "Solicitud de Registro de Administrador";

        Optional<Usuario> admin_data = usuarioService.BuscarUsuario_By_EmailUsuario(email);

        if (admin_data.isPresent()) {
            Usuario admin = admin_data.get();

            Context context = new Context();
            Map<String, Object> model = new HashMap<>();
            model.put("username", admin.getUsernameUsuario());
            model.put("url", url);
            model.put("img_logo", img_logo);
            model.put("img_check", img_check);

            context.setVariables(model);

            String html_template = templateEngine.process("adminrequest-mailtemplate", context);

            helper.setSubject(asunto);
            helper.setText(html_template, true);

            mailSender.send(message);
        }
    }
}
