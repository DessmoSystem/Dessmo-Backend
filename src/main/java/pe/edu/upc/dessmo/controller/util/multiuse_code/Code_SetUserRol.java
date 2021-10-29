package pe.edu.upc.dessmo.controller.util.multiuse_code;

import pe.edu.upc.dessmo.model.Rol;
import pe.edu.upc.dessmo.model.Usuario;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Code_SetUserRol {

    public static void SetUserRol(Usuario usuario, Optional<Rol> rol_data) {
        if (rol_data.isPresent()) {
            Set<Rol> roles = new HashSet<>();
            roles.add(rol_data.get());
            usuario.setRolesUsuario(roles);
        }
    }
}
