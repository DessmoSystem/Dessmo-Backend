package pe.edu.upc.dessmo.dto.request.usuario;

public class UpdateBasicInfoRequest {

    //Atributos
    private String nombreUsuario;
    private String apellidoUsuario;
    private String estadoUsuario;

    //Constructores
    public UpdateBasicInfoRequest() {

    }

    public UpdateBasicInfoRequest(String nombreUsuario, String apellidoUsuario, String estadoUsuario) {
        super();
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.estadoUsuario = estadoUsuario;
    }

    //Getters y Setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }
}
