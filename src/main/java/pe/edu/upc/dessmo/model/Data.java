package pe.edu.upc.dessmo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "data")
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    //Atributos
    @Id
    @Column(name = "id_data", length = 20)
    private String idData;

    @Column(name = "semestre", length = 10)
    private String semestre;

    @Column(name = "edad", length = 19)
    private String edad;

    @Column(name = "genero", length = 9)
    private String genero;

    @Column(name = "tipo_universidad", length = 7)
    private String tipoUniversidad;

    @Column(name = "facultad", length = 55)
    private String facultad;

    @Column(name = "f_ciclos", length = 2)
    private String fCiclos;

    @Column(name = "motivo_abandono", length = 30)
    private String motivoAbandono;

    @Column(name = "categoria", length = 15)
    private String categoria;

    @Column(name = "f_trabaja", length = 2)
    private String fTrabaja;

    @Column(name = "padre_trabaja", length = 10)
    private String padreTrabaja;

    @Column(name = "cubre_pension", length = 25)
    private String cubrePension;

    @Column(name = "gasto_diario", length = 35)
    private String gastoDiario;

    @Column(name = "f_dispositivos", length = 2)
    private String fDispositivos;

    @Column(name = "dispositivo", length = 20)
    private String dispositivo;

    @Column(name = "f_psicologo", length = 2)
    private String fPsicologo;

    @Column(name = "tiempo_psicologo", length = 15)
    private String tiempoPsicologo;

    @Column(name = "razon_psicologo", length = 35)
    private String razonPsicologo;

    @Column(name = "f_asesor", length = 2)
    private String fAsesor;

    @Column(name = "f_estres", length = 2)
    private String fEstres;

    @Column(name = "cursos_retirados", length = 10)
    private String cursosRetirados;

    @Column(name = "horas_estudio", length = 20)
    private String horasEstudio;

    @Column(name = "cursos_jalados")
    private int cursosJalados;

    @Column(name = "f_gruposestudios", length = 2)
    private String fGruposEstudios;

    @Column(name = "f_temprano", length = 2)
    private String fTemprano;

    @Column(name = "faltas_curso", length = 20)
    private String faltasCurso;

    @Column(name = "f_ayuda", length = 2)
    private String fAyuda;

    @Column(name = "f_vivepadres", length = 2)
    private String fVivePadres;

    @Column(name = "integrantes_familia", length = 25)
    private String integrantesFamilia;

    @Column(name = "f_familiaenfermo", length = 2)
    private String fFamiliaEnfermo;

    @Column(name = "f_responsable", length = 2)
    private String fResponsable;

    @ManyToOne
    @JoinTable(name = "usuario_data",
            joinColumns = @JoinColumn(name = "id_data", referencedColumnName = "id_data"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"))
    private Usuario usuarioData;

    //Constructores
    public Data() {
    }

    public Data(String idData, String semestre, String edad, String genero, String tipoUniversidad, String facultad,
                String fCiclos, String motivoAbandono, String categoria, String fTrabaja, String padreTrabaja,
                String cubrePension, String gastoDiario, String fDispositivos, String dispositivo, String fPsicologo,
                String tiempoPsicologo, String razonPsicologo, String fAsesor, String fEstres, String cursosRetirados,
                String horasEstudio, int cursosJalados, String fGruposEstudios, String fTemprano, String faltasCurso,
                String fAyuda, String fVivePadres, String integrantesFamilia, String fFamiliaEnfermo, String fResponsable,
                Usuario usuarioData) {
        this.idData = idData;
        this.semestre = semestre;
        this.edad = edad;
        this.genero = genero;
        this.tipoUniversidad = tipoUniversidad;
        this.facultad = facultad;
        this.fCiclos = fCiclos;
        this.motivoAbandono = motivoAbandono;
        this.categoria = categoria;
        this.fTrabaja = fTrabaja;
        this.padreTrabaja = padreTrabaja;
        this.cubrePension = cubrePension;
        this.gastoDiario = gastoDiario;
        this.fDispositivos = fDispositivos;
        this.dispositivo = dispositivo;
        this.fPsicologo = fPsicologo;
        this.tiempoPsicologo = tiempoPsicologo;
        this.razonPsicologo = razonPsicologo;
        this.fAsesor = fAsesor;
        this.fEstres = fEstres;
        this.cursosRetirados = cursosRetirados;
        this.horasEstudio = horasEstudio;
        this.cursosJalados = cursosJalados;
        this.fGruposEstudios = fGruposEstudios;
        this.fTemprano = fTemprano;
        this.faltasCurso = faltasCurso;
        this.fAyuda = fAyuda;
        this.fVivePadres = fVivePadres;
        this.integrantesFamilia = integrantesFamilia;
        this.fFamiliaEnfermo = fFamiliaEnfermo;
        this.fResponsable = fResponsable;
        this.usuarioData = usuarioData;
    }

    //Getters y Setters
    public String getIdData() {
        return idData;
    }

    public void setIdData(String idData) {
        this.idData = idData;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipoUniversidad() {
        return tipoUniversidad;
    }

    public void setTipoUniversidad(String tipoUniversidad) {
        this.tipoUniversidad = tipoUniversidad;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getfCiclos() {
        return fCiclos;
    }

    public void setfCiclos(String fCiclos) {
        this.fCiclos = fCiclos;
    }

    public String getMotivoAbandono() {
        return motivoAbandono;
    }

    public void setMotivoAbandono(String motivoAbandono) {
        this.motivoAbandono = motivoAbandono;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getfTrabaja() {
        return fTrabaja;
    }

    public void setfTrabaja(String fTrabaja) {
        this.fTrabaja = fTrabaja;
    }

    public String getPadreTrabaja() {
        return padreTrabaja;
    }

    public void setPadreTrabaja(String padreTrabaja) {
        this.padreTrabaja = padreTrabaja;
    }

    public String getCubrePension() {
        return cubrePension;
    }

    public void setCubrePension(String cubrePension) {
        this.cubrePension = cubrePension;
    }

    public String getGastoDiario() {
        return gastoDiario;
    }

    public void setGastoDiario(String gastoDiario) {
        this.gastoDiario = gastoDiario;
    }

    public String getfDispositivos() {
        return fDispositivos;
    }

    public void setfDispositivos(String fDispositivos) {
        this.fDispositivos = fDispositivos;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getfPsicologo() {
        return fPsicologo;
    }

    public void setfPsicologo(String fPsicologo) {
        this.fPsicologo = fPsicologo;
    }

    public String getTiempoPsicologo() {
        return tiempoPsicologo;
    }

    public void setTiempoPsicologo(String tiempoPsicologo) {
        this.tiempoPsicologo = tiempoPsicologo;
    }

    public String getRazonPsicologo() {
        return razonPsicologo;
    }

    public void setRazonPsicologo(String razonPsicologo) {
        this.razonPsicologo = razonPsicologo;
    }

    public String getfAsesor() {
        return fAsesor;
    }

    public void setfAsesor(String fAsesor) {
        this.fAsesor = fAsesor;
    }

    public String getfEstres() {
        return fEstres;
    }

    public void setfEstres(String fEstres) {
        this.fEstres = fEstres;
    }

    public String getCursosRetirados() {
        return cursosRetirados;
    }

    public void setCursosRetirados(String cursosRetirados) {
        this.cursosRetirados = cursosRetirados;
    }

    public String getHorasEstudio() {
        return horasEstudio;
    }

    public void setHorasEstudio(String horasEstudio) {
        this.horasEstudio = horasEstudio;
    }

    public int getCursosJalados() {
        return cursosJalados;
    }

    public void setCursosJalados(int cursosJalados) {
        this.cursosJalados = cursosJalados;
    }

    public String getfGruposEstudios() {
        return fGruposEstudios;
    }

    public void setfGruposEstudios(String fGruposEstudios) {
        this.fGruposEstudios = fGruposEstudios;
    }

    public String getfTemprano() {
        return fTemprano;
    }

    public void setfTemprano(String fTemprano) {
        this.fTemprano = fTemprano;
    }

    public String getFaltasCurso() {
        return faltasCurso;
    }

    public void setFaltasCurso(String faltasCurso) {
        this.faltasCurso = faltasCurso;
    }

    public String getfAyuda() {
        return fAyuda;
    }

    public void setfAyuda(String fAyuda) {
        this.fAyuda = fAyuda;
    }

    public String getfVivePadres() {
        return fVivePadres;
    }

    public void setfVivePadres(String fVivePadres) {
        this.fVivePadres = fVivePadres;
    }

    public String getIntegrantesFamilia() {
        return integrantesFamilia;
    }

    public void setIntegrantesFamilia(String integrantesFamilia) {
        this.integrantesFamilia = integrantesFamilia;
    }

    public String getfFamiliaEnfermo() {
        return fFamiliaEnfermo;
    }

    public void setfFamiliaEnfermo(String fFamiliaEnfermo) {
        this.fFamiliaEnfermo = fFamiliaEnfermo;
    }

    public String getfResponsable() {
        return fResponsable;
    }

    public void setfResponsable(String fResponsable) {
        this.fResponsable = fResponsable;
    }

    public Usuario getUsuarioData() {
        return usuarioData;
    }

    public void setUsuarioData(Usuario usuarioData) {
        this.usuarioData = usuarioData;
    }
}
