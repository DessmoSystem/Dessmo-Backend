package pe.edu.upc.dessmo.dto.response;

public class MotivoAbandonoResponse {

    //Atributos
    private String factorEconomico;

    private String factorPsicologico;

    private String factorTecnologico;

    private String factorResponsabilidadFamiliar;

    //Constructores
    public MotivoAbandonoResponse() {
    }

    public MotivoAbandonoResponse(String factorEconomico, String factorPsicologico, String factorTecnologico,
                                  String factorResponsabilidadFamiliar) {
        this.factorEconomico = factorEconomico;
        this.factorPsicologico = factorPsicologico;
        this.factorTecnologico = factorTecnologico;
        this.factorResponsabilidadFamiliar = factorResponsabilidadFamiliar;
    }

    //Getters y Setters
    public String getFactorEconomico() {
        return factorEconomico;
    }

    public void setFactorEconomico(String factorEconomico) {
        this.factorEconomico = factorEconomico;
    }

    public String getFactorPsicologico() {
        return factorPsicologico;
    }

    public void setFactorPsicologico(String factorPsicologico) {
        this.factorPsicologico = factorPsicologico;
    }

    public String getFactorTecnologico() {
        return factorTecnologico;
    }

    public void setFactorTecnologico(String factorTecnologico) {
        this.factorTecnologico = factorTecnologico;
    }

    public String getFactorResponsabilidadFamiliar() {
        return factorResponsabilidadFamiliar;
    }

    public void setFactorResponsabilidadFamiliar(String factorResponsabilidadFamiliar) {
        this.factorResponsabilidadFamiliar = factorResponsabilidadFamiliar;
    }
}
