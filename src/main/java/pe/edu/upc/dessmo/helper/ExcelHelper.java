package pe.edu.upc.dessmo.helper;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upc.dessmo.model.Data;
import pe.edu.upc.dessmo.model.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean FormatoExcel(MultipartFile archivo) {

        return TYPE.equals(archivo.getContentType());
    }

    public static Set<Data> ExcelToData(InputStream is, Usuario admin) {

        try {
            Set<Data> data_list = new HashSet<>();

            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
                if (index > 0) {
                    Data data = new Data();

                    XSSFRow row = worksheet.getRow(index);

                    DataFormatter formatter = new DataFormatter();

                    String idData = formatter.formatCellValue(row.getCell(0));
                    data.setIdData(idData);

                    String semestre = formatter.formatCellValue(row.getCell(1));
                    data.setSemestre(semestre);

                    String edad = formatter.formatCellValue(row.getCell(2));
                    data.setEdad(edad);

                    String genero = formatter.formatCellValue(row.getCell(3));
                    data.setGenero(genero);

                    String tipoUniversidad = formatter.formatCellValue(row.getCell(4));
                    data.setTipoUniversidad(tipoUniversidad);

                    String facultad = formatter.formatCellValue(row.getCell(5));
                    data.setFacultad(facultad);

                    String fCiclos = formatter.formatCellValue(row.getCell(6));
                    data.setfCiclos(fCiclos);

                    String motivoAbandono = formatter.formatCellValue(row.getCell(7));
                    data.setMotivoAbandono(motivoAbandono);

                    String categoria = formatter.formatCellValue(row.getCell(8));
                    data.setCategoria(categoria);

                    String fTrabaja = formatter.formatCellValue(row.getCell(9));
                    data.setfTrabaja(fTrabaja);

                    String padreTrabaja = formatter.formatCellValue(row.getCell(10));
                    data.setPadreTrabaja(padreTrabaja);

                    String cubrePension = formatter.formatCellValue(row.getCell(11));
                    data.setCubrePension(cubrePension);

                    String gastoDiario = formatter.formatCellValue(row.getCell(12));
                    data.setGastoDiario(gastoDiario);

                    String fDispositivos = formatter.formatCellValue(row.getCell(13));
                    data.setfDispositivos(fDispositivos);

                    String dispositivos = formatter.formatCellValue(row.getCell(14));
                    data.setDispositivo(dispositivos);

                    String fPsicologo = formatter.formatCellValue(row.getCell(15));
                    data.setfPsicologo(fPsicologo);

                    String tiempoPsicologo = formatter.formatCellValue(row.getCell(16));
                    data.setTiempoPsicologo(tiempoPsicologo);

                    String razonPsicologo = formatter.formatCellValue(row.getCell(17));
                    data.setRazonPsicologo(razonPsicologo);

                    String fAsesor = formatter.formatCellValue(row.getCell(18));
                    data.setfAsesor(fAsesor);

                    String fEstres = formatter.formatCellValue(row.getCell(19));
                    data.setfEstres(fEstres);

                    String cursosRetirados = formatter.formatCellValue(row.getCell(20));
                    data.setCursosRetirados(cursosRetirados);

                    String horasEstudio = formatter.formatCellValue(row.getCell(21));
                    data.setHorasEstudio(horasEstudio);

                    data.setCursosJalados((int) row.getCell(22).getNumericCellValue());

                    String fGruposEstudios = formatter.formatCellValue(row.getCell(23));
                    data.setfGruposEstudios(fGruposEstudios);

                    String fTemprano = formatter.formatCellValue(row.getCell(24));
                    data.setfTemprano(fTemprano);

                    String faltasCurso = formatter.formatCellValue(row.getCell(25));
                    data.setFaltasCurso(faltasCurso);

                    String fAyuda = formatter.formatCellValue(row.getCell(26));
                    data.setfAyuda(fAyuda);

                    String fVivePadres = formatter.formatCellValue(row.getCell(27));
                    data.setfVivePadres(fVivePadres);

                    String integrantesFamilia = formatter.formatCellValue(row.getCell(28));
                    data.setIntegrantesFamilia(integrantesFamilia);

                    String fFamiliaEnfermo = formatter.formatCellValue(row.getCell(29));
                    data.setfFamiliaEnfermo(fFamiliaEnfermo);

                    String fResponsable = formatter.formatCellValue(row.getCell(30));
                    data.setfResponsable(fResponsable);

                    data.setUsuarioData(admin);

                    data_list.add(data);

                }
            }

            return data_list;
        } catch (IOException e) {
            throw new RuntimeException("Error al analizar el archivo Excel: " + e.getMessage());
        }
    }
}
