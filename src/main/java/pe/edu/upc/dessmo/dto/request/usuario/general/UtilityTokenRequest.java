package pe.edu.upc.dessmo.dto.request.usuario.general;

public class UtilityTokenRequest {

    //Atributos
    private String utilityToken;

    //Constructores
    public UtilityTokenRequest() {
    }

    public UtilityTokenRequest(String utilityToken) {
        this.utilityToken = utilityToken;
    }

    //Getters y Setters
    public String getUtilityToken() {
        return utilityToken;
    }

    public void setUtilityToken(String utilityToken) {
        this.utilityToken = utilityToken;
    }
}