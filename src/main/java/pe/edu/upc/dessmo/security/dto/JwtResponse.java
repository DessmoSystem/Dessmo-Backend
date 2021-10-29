package pe.edu.upc.dessmo.security.dto;

import org.springframework.security.core.GrantedAuthority;
import pe.edu.upc.dessmo.dto.response.general.ImagenResponse;

import java.util.Collection;

public class JwtResponse {

    //Atributos
    private String type = "Bearer";
    private String token;
    private Long idUsuario;
    private String usernameUsuario;
    private ImagenResponse fotoUsuario;
    private Collection<? extends GrantedAuthority> authorities;

    //Constructores
    public JwtResponse() {
    }

    public JwtResponse(String token, Long idUsuario, String usernameUsuario, ImagenResponse fotoUsuario,
                       Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.idUsuario = idUsuario;
        this.usernameUsuario = usernameUsuario;
        this.fotoUsuario = fotoUsuario;
        this.authorities = authorities;
    }

    //Getters y Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsernameUsuario() {
        return usernameUsuario;
    }

    public void setUsernameUsuario(String usernameUsuario) {
        this.usernameUsuario = usernameUsuario;
    }

    public ImagenResponse getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(ImagenResponse fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
