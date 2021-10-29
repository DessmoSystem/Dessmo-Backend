package pe.edu.upc.dessmo.controller.usuario.signin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.dessmo.controller.util.multiuse_code.Code_SigninValidations;
import pe.edu.upc.dessmo.dto.request.usuario.SigninRequest;
import pe.edu.upc.dessmo.dto.response.general.MessageResponse;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.security.jwt.JwtProvider;
import pe.edu.upc.dessmo.service.IUsuarioService;
import pe.edu.upc.dessmo.serviceimpl.UserDetailsImpl;

import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class SigninUsuarioController {
    final
    AuthenticationManager authenticationManager;

    final
    JwtProvider jwtProvider;

    final
    IUsuarioService usuarioService;

    public SigninUsuarioController(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
                                   IUsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.usuarioService = usuarioService;
    }


    @PostMapping("/usuario/signin")
    public ResponseEntity<?> SignInUsuario(@RequestBody SigninRequest signInRequest) {

        Optional<Usuario> usuario_data = usuarioService.BuscarUsuario_By_UsernameOrEmail(signInRequest.getUsernameUsuario());

        if (usuario_data.isPresent()) {
            Usuario usuario = usuario_data.get();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getUsernameUsuario(), signInRequest.getPasswordUsuario())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                return Code_SigninValidations.SigninUsuario(jwt, userDetails);
            } else {
                return new ResponseEntity<>(new MessageResponse("No cumple con los permisos para acceder al sistema."), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("Usuario no encontrado en el sistema."), HttpStatus.NOT_FOUND);
        }
    }
}
