package pe.edu.upc.dessmo.serviceimpl;

import org.springframework.stereotype.Service;
import pe.edu.upc.dessmo.enums.RolNombre;
import pe.edu.upc.dessmo.model.Rol;
import pe.edu.upc.dessmo.repository.IRolDAO;
import pe.edu.upc.dessmo.service.IRolService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RolServiceImpl implements IRolService {

    final
    IRolDAO data;

    public RolServiceImpl(IRolDAO data) {
        this.data = data;
    }

    @Override
    public Optional<Rol> BuscarRol_Nombre(RolNombre rol) {
        return data.findByNombreRol(rol);
    }
}
