package pe.edu.upc.dessmo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "satisfaccion")
public class Satisfaccion implements Serializable {

    private static final long serialVersionUID = 1L;

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_satisfaccion")
    private Long idSatisfaccion;

    @Column(name = "valor_satisfaccion", length = 30)
    private String valorSatisfaccion;

    @Column(name = "fecha_satisfaccion")
    private LocalDateTime fechaSatisfaccion;

    @ManyToOne
    @JoinTable(name = "usuario_satisfacciones",
            joinColumns = @JoinColumn(name = "id_satisfaccion", referencedColumnName = "id_satisfaccion"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"))
    private Usuario usuarioSatisfaccion;

    //Constructores
    public Satisfaccion() {
    }

    public Satisfaccion(String valorSatisfaccion, LocalDateTime fechaSatisfaccion, Usuario usuarioSatisfaccion) {
        this.valorSatisfaccion = valorSatisfaccion;
        this.fechaSatisfaccion = fechaSatisfaccion;
        this.usuarioSatisfaccion = usuarioSatisfaccion;
    }

    //Getters y Setters
    public Long getIdSatisfaccion() {
        return idSatisfaccion;
    }

    public void setIdSatisfaccion(Long idSatisfaccion) {
        this.idSatisfaccion = idSatisfaccion;
    }

    public String getValorSatisfaccion() {
        return valorSatisfaccion;
    }

    public void setValorSatisfaccion(String valorSatisfaccion) {
        this.valorSatisfaccion = valorSatisfaccion;
    }

    public LocalDateTime getFechaSatisfaccion() {
        return fechaSatisfaccion;
    }

    public void setFechaSatisfaccion(LocalDateTime fechaSatisfaccion) {
        this.fechaSatisfaccion = fechaSatisfaccion;
    }

    public Usuario getUsuarioSatisfaccion() {
        return usuarioSatisfaccion;
    }

    public void setUsuarioSatisfaccion(Usuario usuarioSatisfaccion) {
        this.usuarioSatisfaccion = usuarioSatisfaccion;
    }
}
