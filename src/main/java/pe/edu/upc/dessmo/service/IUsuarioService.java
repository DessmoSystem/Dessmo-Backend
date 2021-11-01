package pe.edu.upc.dessmo.service;

import pe.edu.upc.dessmo.model.Usuario;

import java.util.Optional;
import java.util.Set;

public interface IUsuarioService {

    Optional<Usuario> BuscarUsuario_By_IDUsuario(Long id_usuario);

    Optional<Usuario> BuscarUsuario_By_EmailUsuario(String email_usuario);

    Optional<Usuario> BuscarUsuario_By_UsernameOrEmail(String username_or_email);

    Optional<Usuario> BuscarUsuario_By_IDUtilityToken(Long id_utilitytoken);

    Set<Usuario> BuscarUsuarios_By_EstadoUsuario(String estado_usuario);

    Boolean ValidarUsername(String username_usuario);

    void GuardarUsuario(Usuario usuario);

    void EliminarUsuario(Long id_usuario);
}
