package pe.edu.upc.dessmo.service;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.upc.dessmo.model.Data;
import pe.edu.upc.dessmo.model.Usuario;

import java.util.Set;

public interface IDataService {

    Set<Data> MostrarData();

    Set<Data> BuscarData_By_MotivoAbandono(String motivo_abandono);

    void GuardarData(MultipartFile archivo, Usuario admin);
}
