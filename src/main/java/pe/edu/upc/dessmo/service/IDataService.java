package pe.edu.upc.dessmo.service;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.upc.dessmo.model.Usuario;

public interface IDataService {

    void GuardarData(MultipartFile archivo, Usuario admin);
}
