package pe.edu.upc.dessmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.dessmo.model.Imagen;

import java.util.Optional;


@Repository
public interface IImagenDAO extends JpaRepository<Imagen, Long> {

    Optional<Imagen> findByNombreImagen(String nombreimagen);
}
