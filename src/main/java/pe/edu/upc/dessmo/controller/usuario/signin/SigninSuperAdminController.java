package pe.edu.upc.dessmo.controller.usuario.signin;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pe.edu.upc.dessmo.dto.request.usuario.SigninRequest;
import pe.edu.upc.dessmo.dto.response.general.MessageResponse;
import pe.edu.upc.dessmo.model.Imagen;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.security.dto.JwtResponse;
import pe.edu.upc.dessmo.security.jwt.JwtProvider;
import pe.edu.upc.dessmo.service.IImagenService;
import pe.edu.upc.dessmo.service.IUsuarioService;
import pe.edu.upc.dessmo.serviceimpl.UserDetailsImpl;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SigninSuperAdminController {

    final
    AuthenticationManager authenticationManager;

    final
    JwtProvider jwtProvider;

    final
    IUsuarioService usuarioService;

    final
    IImagenService imagenService;

    public SigninSuperAdminController(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
                                      IUsuarioService usuarioService, IImagenService imagenService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.usuarioService = usuarioService;
        this.imagenService = imagenService;
    }

    @PostMapping("/superadmin/signin")
    public ResponseEntity<?> SignInSuperAdmin(@RequestBody SigninRequest signInRequest) {

        Optional<Usuario> superadmin_data = usuarioService.BuscarUsuario_By_UsernameOrEmail(signInRequest.getUsernameUsuario());

        if (superadmin_data.isPresent()) {
            Usuario superadmin = superadmin_data.get();

            Imagen foto = superadmin.getImagenUsuario();

            if (foto == null) {
                //Asignando Default Foto: SuperAdmin
                try {
                    String nombre_foto = UUID.randomUUID() + superadmin.getIdUsuario().toString() + UUID.randomUUID()
                            + ".png";

                    String url_foto = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/photos/")
                            .path(nombre_foto)
                            .toUriString();

                    InputStream fotoStream = getClass().getResourceAsStream("/static/img/SuperAdminUser.png");
                    assert fotoStream != null;
                    byte[] file_foto = IOUtils.toByteArray(fotoStream);

                    Imagen imagen = new Imagen(
                            nombre_foto,
                            "image/png",
                            url_foto,
                            file_foto,
                            superadmin
                    );

                    imagenService.GuardarImagen(imagen);
                } catch (Exception e) {
                    return new ResponseEntity<>(new MessageResponse("Ocurrió un error al iniciar sesión." + e), HttpStatus.EXPECTATION_FAILED);
                }
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(superadmin.getUsernameUsuario(), signInRequest.getPasswordUsuario())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERADMIN"))) {
                if (userDetails.getEstadoUsuario().equals("ACTIVO")) {
                    return new ResponseEntity<>(new JwtResponse(
                            jwt,
                            userDetails.getIdUsuario(),
                            null,
                            userDetails.getUsername(),
                            userDetails.getFotoUsuario(),
                            userDetails.getAuthorities()
                    ), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new MessageResponse("El Usuario no se encuentra habilitado para acceder al sistema."),
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse("No cumple con los permisos para acceder al sistema."),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("Usuario no encontrado en el sistema."),
                    HttpStatus.NOT_FOUND);
        }
    }
}
