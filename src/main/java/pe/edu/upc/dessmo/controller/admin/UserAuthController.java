package pe.edu.upc.dessmo.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.dessmo.dto.response.UserAuthResponse;
import pe.edu.upc.dessmo.dto.response.general.MessageResponse;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.service.IImagenService;
import pe.edu.upc.dessmo.service.IUsuarioService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserAuthController {

    final
    IUsuarioService usuarioService;

    final
    IImagenService imagenService;

    final
    JavaMailSender mailSender;

    public UserAuthController(IUsuarioService usuarioService, IImagenService imagenService, JavaMailSender mailSender) {
        this.usuarioService = usuarioService;
        this.imagenService = imagenService;
        this.mailSender = mailSender;
    }

    @GetMapping("/usuarios/display/verify")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> ShowAuthUsers() {

        Set<UserAuthResponse> list_userauth = new HashSet<>();

        usuarioService.BuscarUsuarios_By_EstadoUsuario("VERIFICADO").forEach(
                usuario -> list_userauth.add(
                        new UserAuthResponse(
                                usuario.getIdUsuario(),
                                usuario.getNombreUsuario() + usuario.getApellidoUsuario(),
                                usuario.getFecharegistroUsuario()
                        )));

        return new ResponseEntity<>(list_userauth, HttpStatus.OK);
    }

    @PostMapping("/usuario/{id_usuario}/request/allow")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> AllowUser(@PathVariable("id_usuario") Long id_usuario)
            throws MessagingException, UnsupportedEncodingException {
        Optional<Usuario> usuario_data = usuarioService.BuscarUsuario_By_IDUsuario(id_usuario);

        if (usuario_data.isPresent()) {
            Usuario usuario = usuario_data.get();

            usuario.setEstadoUsuario("ACTIVO");
            usuarioService.GuardarUsuario(usuario);

            EnviarCorreo_Aprobacion(usuario.getEmailUsuario());

            return new ResponseEntity<>(new MessageResponse("Se ha autorizado la solicitud del usuario " +
                    "satisfactoriamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("Ocurrió un error al querer autorizar la solicitud del " +
                    "usuario"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/usuario/{id_usuario}/request/deny")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> DenyUser(@PathVariable("id_usuario") Long id_usuario)
            throws MessagingException, UnsupportedEncodingException {
        Optional<Usuario> usuario_data = usuarioService.BuscarUsuario_By_IDUsuario(id_usuario);

        if (usuario_data.isPresent()) {
            Usuario usuario = usuario_data.get();

            usuarioService.EliminarUsuario(id_usuario);

            EnviarCorreo_Rechazo(usuario.getEmailUsuario());

            return new ResponseEntity<>(new MessageResponse("Se ha rechazado la solicitud del usuario " +
                    "satisfactoriamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("Ocurrió un error al querer autorizar la solicitud del " +
                    "usuario"), HttpStatus.NOT_FOUND);
        }
    }

    private void EnviarCorreo_Aprobacion(String email) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("fenosys.assistant@gmail.com", "Dessmo Support");
        helper.setTo(email);

        String asunto = "Autorización de ingreso a Dessmo";

        String contenido =
                "<h2>Hola,</h1>" +
                        "<p>Se autorizó tu solicitud de registro. Ya puedes ingresar al sistema sin problemas.</p>";

        helper.setSubject(asunto);
        helper.setText(contenido, true);

        mailSender.send(message);
    }

    private void EnviarCorreo_Rechazo(String email) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("fenosys.assistant@gmail.com", "Dessmo Support");
        helper.setTo(email);

        String asunto = "Autorización de ingreso a Dessmo";

        String contenido =
                "<h2>Hola,</h1>" +
                        "<p>Se rechazó tu solicitud de registro.</p>";

        helper.setSubject(asunto);
        helper.setText(contenido, true);

        mailSender.send(message);
    }
}
