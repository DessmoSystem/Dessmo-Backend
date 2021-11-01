package pe.edu.upc.dessmo.serviceimpl;

import org.springframework.stereotype.Service;
import pe.edu.upc.dessmo.model.Satisfaccion;
import pe.edu.upc.dessmo.repository.ISatisfaccionDAO;
import pe.edu.upc.dessmo.service.ISatisfaccionService;

import javax.transaction.Transactional;

@Service
@Transactional
public class SatisfaccionServiceImpl implements ISatisfaccionService {

    final
    ISatisfaccionDAO data;

    public SatisfaccionServiceImpl(ISatisfaccionDAO data) {
        this.data = data;
    }

    @Override
    public void GuardarSatisfaccion(Satisfaccion satisfaccion) {
        data.save(satisfaccion);
    }
}
