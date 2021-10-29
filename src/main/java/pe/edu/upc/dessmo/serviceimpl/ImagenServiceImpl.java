package pe.edu.upc.dessmo.serviceimpl;

import org.springframework.stereotype.Service;
import pe.edu.upc.dessmo.model.Imagen;
import pe.edu.upc.dessmo.repository.IImagenDAO;
import pe.edu.upc.dessmo.service.IImagenService;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class ImagenServiceImpl implements IImagenService {

    final
    IImagenDAO data;

    public ImagenServiceImpl(IImagenDAO data) {
        this.data = data;
    }

    @Override
    public Optional<Imagen> BuscarImagen_ID(Long id_imagen) {
        return data.findById(id_imagen);
    }

    @Override
    public Optional<Imagen> BuscarImagen_Nombre(String nombre_imagen) {
        return data.findByNombreImagen(nombre_imagen);
    }

    @Override
    public void GuardarImagen(Imagen imagen) {
        data.save(imagen);
    }
}

