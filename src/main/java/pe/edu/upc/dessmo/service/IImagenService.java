package pe.edu.upc.dessmo.service;

import pe.edu.upc.dessmo.model.Imagen;

import java.util.Optional;

public interface IImagenService {

    Optional<Imagen> BuscarImagen_ID(Long id_imagen);

    Optional<Imagen> BuscarImagen_Nombre(String nombre_imagen);

    void GuardarImagen(Imagen imagen);
}