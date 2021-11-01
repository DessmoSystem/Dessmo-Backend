package pe.edu.upc.dessmo.controller.usuario;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pe.edu.upc.dessmo.dto.request.usuario.general.EmailRequest;
import pe.edu.upc.dessmo.dto.request.usuario.general.UpdatePasswordRequest;
import pe.edu.upc.dessmo.dto.response.general.MessageResponse;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.model.UtilityToken;
import pe.edu.upc.dessmo.service.IUsuarioService;
import pe.edu.upc.dessmo.service.IUtilityTokenService;
import pe.edu.upc.dessmo.tools.UtilityDessmo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestorePasswordController {

    final
    IUsuarioService usuarioService;

    final
    IUtilityTokenService utilityTokenService;

    final
    JavaMailSender mailSender;

    final
    PasswordEncoder passwordEncoder;

    final
    TemplateEngine templateEngine;

    @Value("${front.baseurl}")
    private String baseurl;

    @Value("${image.logo.url}")
    private String img_logo;

    @Value("${image.lock.url}")
    private String img_lock;

    public RestorePasswordController(IUsuarioService usuarioService, IUtilityTokenService utilityTokenService,
                                     JavaMailSender mailSender, PasswordEncoder passwordEncoder,
                                     TemplateEngine templateEngine) {
        this.usuarioService = usuarioService;
        this.utilityTokenService = utilityTokenService;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.templateEngine = templateEngine;
    }

    @PostMapping("/restore_password/request")
    public ResponseEntity<?> RestartPasswordSendEmail(@RequestBody EmailRequest emailRequest,
                                                      HttpServletRequest request) {

        Optional<Usuario> usuario_data = usuarioService.BuscarUsuario_By_EmailUsuario(emailRequest.getEmailUsuario());

        if (usuario_data.isPresent()) {
            Usuario usuario = usuario_data.get();

            Set<UtilityToken> lista_utilitytoken =
                    new HashSet<>(utilityTokenService.BuscarUtilityToken_By_IDUsuarioAndRazonUtilityToken(
                            usuario.getIdUsuario(),
                            "Restore Password"));

            if ((long) lista_utilitytoken.size() < 1) {
                try {
                    String token = RandomString.make(50);

                    UtilityToken utilityToken = new UtilityToken(
                            token,
                            "Restore Password",
                            LocalDateTime.now().plusMinutes(10),
                            usuario
                    );
                    utilityTokenService.GuardarUtilityToken(utilityToken);

                    String url = UtilityDessmo.GenerarUrl(request) + "/api/restore_password_gateway?token=" + token;

                    EnviarCorreo(emailRequest.getEmailUsuario(), url);
                } catch (UnsupportedEncodingException e) {
                    return new ResponseEntity<>(new MessageResponse("Error: " + e),
                            HttpStatus.BAD_REQUEST);
                } catch (MessagingException e) {
                    return new ResponseEntity<>(new MessageResponse("Error al enviar el email."),
                            HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(new MessageResponse("Revise su bandeja de entrada para continuar con el " +
                        "proceso de Restauración de Contraseña. Recuerde que dispone de no más de 10 minutos para " +
                        "culminar con el proceso. De lo contrario, deberá efectuar una nueva solicitud."),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse("Ya se solicitó un proceso de Restauración previamente " +
                        "con este correo electrónico."),
                        HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("No se encuentra el email solicitado en nuestro sistema."),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/restore_password_gateway")
    void RedirectSignupAdminRequest(HttpServletResponse response, @Param(value = "token") String token) throws IOException {

        Optional<UtilityToken> utilitytoken_data = utilityTokenService.BuscarUtilityToken_By_Token(token);

        if (utilitytoken_data.isPresent()) {
            Optional<Usuario> usuario_data =
                    usuarioService.BuscarUsuario_By_IDUtilityToken(utilitytoken_data.get().getIdUtilityToken());

            if (usuario_data.isPresent()) {
                Usuario usuario = usuario_data.get();

                usuario.setEstadoUsuario("BLOQUEADO");
                usuarioService.GuardarUsuario(usuario);

                response.sendRedirect(baseurl + "/restore/password/" + token);
            } else {
                response.sendRedirect(baseurl + "/error/403");
            }
        } else {
            response.sendRedirect(baseurl + "/error/403");
        }
    }

    @PutMapping("/restore_password/update")
    public ResponseEntity<?> UpdatePasswordProcess(@RequestBody UpdatePasswordRequest updatePasswordRequest) {

        Optional<UtilityToken> utilitytoken_data = utilityTokenService.BuscarUtilityToken_By_Token(updatePasswordRequest.getUtilityToken());

        if (utilitytoken_data.isPresent()) {
            UtilityToken utilitytoken = utilitytoken_data.get();

            Optional<Usuario> usuario_data = usuarioService.BuscarUsuario_By_IDUtilityToken(utilitytoken.getIdUtilityToken());

            if (usuario_data.isPresent()) {
                Usuario usuario = usuario_data.get();

                usuario.setPasswordUsuario(passwordEncoder.encode(updatePasswordRequest.getPasswordUsuario()));

                //Cambiando Estado de Cuenta: ACTIVO
                usuario.setEstadoUsuario("ACTIVO");

                usuarioService.GuardarUsuario(usuario);
                utilityTokenService.EliminarUtilityToken(utilitytoken.getIdUtilityToken());

                return new ResponseEntity<>(new MessageResponse("Contraseña actualizada satisfactoriamente"),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse("Ocurrió un error al procesar su solicitud."),
                        HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("El proceso de restauración ya no se encuentra disponible."),
                    HttpStatus.NOT_FOUND);
        }
    }

    private void EnviarCorreo(String email, String url) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("dessmosystem@gmail.com", "Dessmo Support");
        helper.setTo(email);

        String asunto = "Restablecimiento de Contraseña";

        Context context = new Context();
        Map<String, Object> model = new HashMap<>();
        model.put("url", url);
        model.put("img_logo", img_logo);
        model.put("img_lock", img_lock);

        context.setVariables(model);

        String html_template = templateEngine.process("restorepassword-mailtemplate", context);

        helper.setSubject(asunto);
        helper.setText(html_template, true);

        mailSender.send(message);
    }
}
