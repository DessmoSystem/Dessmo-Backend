package pe.edu.upc.dessmo.tools;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.model.UtilityToken;
import pe.edu.upc.dessmo.service.IUsuarioService;
import pe.edu.upc.dessmo.service.IUtilityTokenService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
@Transactional
public class MaintainceDessmo {

    final
    IUtilityTokenService utilityTokenService;

    final
    IUsuarioService usuarioService;

    public MaintainceDessmo(IUtilityTokenService utilityTokenService, IUsuarioService usuarioService) {
        this.utilityTokenService = utilityTokenService;
        this.usuarioService = usuarioService;
    }

    @Scheduled(fixedRate = 60000)
    public void EliminarUsuariosTimedOutSignupAdmin() {
        Set<UtilityToken> utilitytoken_list = utilityTokenService.BuscarUtilityToken_By_ExpiracionAndRazon(LocalDateTime.now(),
                "Signup Admin");

        UtilityTokenList(utilitytoken_list);
    }

    @Scheduled(fixedRate = 60000)
    public void EliminarUsuariosTimedOutSignupUsuario() {
        Set<UtilityToken> utilitytoken_list = utilityTokenService.BuscarUtilityToken_By_ExpiracionAndRazon(LocalDateTime.now(),
                "Signup Usuario Verify");

        UtilityTokenList(utilitytoken_list);
    }

    @Scheduled(fixedRate = 60000)
    public void EliminarRestoreToken() {
        Set<UtilityToken> utilitytoken_list = utilityTokenService.BuscarUtilityToken_By_ExpiracionAndRazon(LocalDateTime.now(),
                "Restore Password");

        for (UtilityToken restoretoken : utilitytoken_list) {
            utilityTokenService.EliminarUtilityToken(restoretoken.getIdUtilityToken());
        }
    }

    private void UtilityTokenList(Set<UtilityToken> utilitytoken_list) {
        for (UtilityToken utilitytoken : utilitytoken_list) {
            Optional<Usuario> usuario_timedout = usuarioService.BuscarUsuario_By_UtilityToken(utilitytoken.getIdUtilityToken());

            if (usuario_timedout.isPresent()) {
                Usuario usuario = usuario_timedout.get();

                usuarioService.EliminarUsuario(usuario.getIdUsuario());
            }
        }
    }
}

