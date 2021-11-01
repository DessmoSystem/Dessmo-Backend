package pe.edu.upc.dessmo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.dessmo.dto.request.SatisfaccionRequest;
import pe.edu.upc.dessmo.dto.response.general.MessageResponse;
import pe.edu.upc.dessmo.model.Satisfaccion;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.service.ISatisfaccionService;
import pe.edu.upc.dessmo.service.IUsuarioService;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SatisfaccionController {

    final
    IUsuarioService usuarioService;

    final
    ISatisfaccionService satisfaccionService;

    public SatisfaccionController(IUsuarioService usuarioService, ISatisfaccionService satisfaccionService) {
        this.usuarioService = usuarioService;
        this.satisfaccionService = satisfaccionService;
    }

    @GetMapping("/user/{id_user}/satisfaccion/save")
    public ResponseEntity<?> GuardarSatisfaccionUsuario(@PathVariable("id_user") Long id_user,
                                                        @RequestBody SatisfaccionRequest satisfaccionRequest) {

        Optional<Usuario> usuario_data = usuarioService.BuscarUsuario_By_IDUsuario(id_user);

        if (usuario_data.isPresent()) {
            Usuario user = usuario_data.get();

            Satisfaccion satisfaccion = new Satisfaccion(
                    satisfaccionRequest.getValorSatisfaccion(),
                    LocalDateTime.now(),
                    user
            );

            satisfaccionService.GuardarSatisfaccion(satisfaccion);

            return new ResponseEntity<>(new MessageResponse("Se ha guardado su nivel de satisfacción del uso " +
                    "del sistema correctamente."),
                    HttpStatus.OK);

        } else {
            return new ResponseEntity<>(new MessageResponse("Ocurrió un error al guardar el nivel de satisfacción " +
                    "del usuario."),
                    HttpStatus.NOT_FOUND);
        }
    }
}
