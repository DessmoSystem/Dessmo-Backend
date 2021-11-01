package pe.edu.upc.dessmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.dessmo.model.Satisfaccion;

@Repository
public interface ISatisfaccionDAO extends JpaRepository<Satisfaccion, Long> {

}
