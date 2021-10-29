package pe.edu.upc.dessmo.serviceimpl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upc.dessmo.helper.ExcelHelper;
import pe.edu.upc.dessmo.model.Data;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.repository.IDataDAO;
import pe.edu.upc.dessmo.service.IDataService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Set;

@Service
@Transactional
public class DataServiceImpl implements IDataService {

    final
    IDataDAO dao;

    public DataServiceImpl(IDataDAO dao) {
        this.dao = dao;
    }

    @Override
    public void GuardarData(MultipartFile archivo, Usuario admin) {
        try {
            Set<Data> lote_data = ExcelHelper.ExcelToData(archivo.getInputStream(), admin);
            dao.saveAll(lote_data);
        } catch (IOException e) {
            throw new RuntimeException("Ocurrió un error al guardar los datos de Excel: " + e.getMessage());
        }
    }
}
