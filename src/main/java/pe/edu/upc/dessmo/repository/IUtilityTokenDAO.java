package pe.edu.upc.dessmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.dessmo.model.UtilityToken;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;


@Repository
public interface IUtilityTokenDAO extends JpaRepository<UtilityToken, Long> {

    Optional<UtilityToken> findByTokenUtilityToken(String token_utilitytoken);

    @Query("SELECT ut FROM UtilityToken ut WHERE ut.expiracionUtilityToken < ?1 AND ut.razonUtilityToken LIKE ?2")
    Set<UtilityToken> findUtilityTokenByExpiracionAndRazon(LocalDateTime expiracion_utilitytoken, String razon_utilitytoken);

    /*@Query(value = "SELECT utu.*, ut.*, u.* " +
            "FROM utilitytokens_usuario utu " +
            "JOIN utilitytoken ut ON utu.id_utilitytoken = ut.id_utilitytoken " +
            "JOIN usuario u ON utu.id_usuario = u.id_usuario " +
            "WHERE u.id_usuario = ?1 AND ut.razon_utilitytoken LIKE ?2", nativeQuery = true)*/
    @Query("SELECT ut FROM UtilityToken ut JOIN Usuario u ON u.idUsuario = ut.usuarioUtilityToken.idUsuario " +
            "WHERE u.idUsuario = ?1 AND ut.razonUtilityToken LIKE ?2")
    Set<UtilityToken> findUtilityTokensByIDUsuarioAndRazon(Long id_usuario, String razon_utilitytoken);
}
