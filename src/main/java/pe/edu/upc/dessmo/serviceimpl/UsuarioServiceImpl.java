package pe.edu.upc.dessmo.serviceimpl;

import org.springframework.stereotype.Service;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.repository.IUsuarioDAO;
import pe.edu.upc.dessmo.service.IUsuarioService;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {

    final
    IUsuarioDAO data;

    public UsuarioServiceImpl(IUsuarioDAO data) {
        this.data = data;
    }

    @Override
    public Optional<Usuario> BuscarUsuario_By_IDUsuario(Long id_usuario) {
        return data.findById(id_usuario);
    }

    @Override
    public Optional<Usuario> BuscarUsuario_By_EmailUsuario(String email_usuario) {
        return data.findByEmailUsuario(email_usuario);
    }

    @Override
    public Optional<Usuario> BuscarUsuario_By_UsernameOrEmail(String username_or_email) {
        return data.findByUsernameOrEmail(username_or_email);
    }

    @Override
    public Optional<Usuario> BuscarUsuario_By_IDUtilityToken(Long id_utilitytoken) {
        return data.findByIdUtilitytoken(id_utilitytoken);
    }

    @Override
    public Set<Usuario> BuscarUsuarios_By_EstadoUsuario(String estado_usuario) {
        return data.findByEstadoUsuario(estado_usuario);
    }

    @Override
    public Boolean ValidarUsername(String username_usuario) {
        return data.existsByUsernameUsuario(username_usuario);
    }

    @Override
    public void GuardarUsuario(Usuario usuario) {
        data.save(usuario);
    }

    @Override
    public void EliminarUsuario(Long id_usuario) {
        data.deleteById(id_usuario);
    }
}
