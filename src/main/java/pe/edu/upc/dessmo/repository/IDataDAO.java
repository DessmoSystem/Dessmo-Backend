package pe.edu.upc.dessmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.dessmo.model.Data;

@Repository
public interface IDataDAO extends JpaRepository<Data, String> {
}
