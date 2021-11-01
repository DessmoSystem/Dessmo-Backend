package pe.edu.upc.dessmo.dto.request;

public class SatisfaccionRequest {

    //Atributos
    private String valorSatisfaccion;

    //Constructores
    public SatisfaccionRequest() {
    }

    public SatisfaccionRequest(String valorSatisfaccion) {
        this.valorSatisfaccion = valorSatisfaccion;
    }

    //Getters y Setters
    public String getValorSatisfaccion() {
        return valorSatisfaccion;
    }

    public void setValorSatisfaccion(String valorSatisfaccion) {
        this.valorSatisfaccion = valorSatisfaccion;
    }
}
