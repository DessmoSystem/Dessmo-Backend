package pe.edu.upc.dessmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.dessmo.model.Data;

import java.util.Set;

@Repository
public interface IDataDAO extends JpaRepository<Data, String> {

    @Query("SELECT d FROM Data d")
    Set<Data> findData();

    @Query("SELECT d FROM Data d WHERE d.motivoAbandono LIKE ?1")
    Set<Data> findDataByMotivoAbandono(String motivo_abandono);
}
