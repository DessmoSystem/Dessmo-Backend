package pe.edu.upc.dessmo.dto.response;

import java.time.LocalDate;

public class UserAuthResponse {

    //Atributos
    private Long idUsuario;
    private String nombreUsuario;
    private LocalDate fecharegistroUsuario;

    //Constructores
    public UserAuthResponse() {
    }

    public UserAuthResponse(Long idUsuario, String nombreUsuario, LocalDate fecharegistroUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.fecharegistroUsuario = fecharegistroUsuario;
    }

    //Getters y Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public LocalDate getFecharegistroUsuario() {
        return fecharegistroUsuario;
    }

    public void setFecharegistroUsuario(LocalDate fecharegistroUsuario) {
        this.fecharegistroUsuario = fecharegistroUsuario;
    }
}
