package pe.edu.upc.dessmo.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upc.dessmo.dto.response.general.MessageResponse;
import pe.edu.upc.dessmo.helper.ExcelHelper;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.service.IDataService;
import pe.edu.upc.dessmo.service.IUsuarioService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UploadDataController {

    final
    IUsuarioService usuarioService;

    final
    IDataService dataService;

    public UploadDataController(IUsuarioService usuarioService, IDataService dataService) {
        this.usuarioService = usuarioService;
        this.dataService = dataService;
    }

    @PostMapping("/admin/{id_admin}/data/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SubirLoteDatosExcel(@PathVariable("id_admin") Long id_admin,
                                                 @RequestParam("excel") MultipartFile excel) {

        Optional<Usuario> admin_data = usuarioService.BuscarUsuario_By_IDUsuario(id_admin);

        if (admin_data.isPresent()) {

            Usuario admin = admin_data.get();

            if (ExcelHelper.FormatoExcel(excel)) {
                try {
                    dataService.GuardarData(excel, admin);
                    return new ResponseEntity<>(new MessageResponse("Se subió los datos del archivo Excel correctamente"),
                            HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>(new MessageResponse("No se puede cargar el archivo: " +
                            excel.getOriginalFilename()),
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse("El tipo de archivo no corresponde a uno de Excel."),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("Ocurrió un error al asignar el administrador presente"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
