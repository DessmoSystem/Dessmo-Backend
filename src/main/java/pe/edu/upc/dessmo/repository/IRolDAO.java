package pe.edu.upc.dessmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.dessmo.enums.RolNombre;
import pe.edu.upc.dessmo.model.Rol;

import java.util.Optional;

@Repository
public interface IRolDAO extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByNombreRol(RolNombre rol);
}
