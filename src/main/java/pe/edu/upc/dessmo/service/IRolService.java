package pe.edu.upc.dessmo.service;

import pe.edu.upc.dessmo.enums.RolNombre;
import pe.edu.upc.dessmo.model.Rol;

import java.util.Optional;

public interface IRolService {

    Optional<Rol> BuscarRol_Nombre(RolNombre rol);
}
