package pe.edu.upc.dessmo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.dessmo.dto.response.MotivoAbandonoResponse;
import pe.edu.upc.dessmo.model.Data;
import pe.edu.upc.dessmo.service.IDataService;

import java.text.NumberFormat;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ResumenFactorController {

    final
    IDataService dataService;

    public ResumenFactorController(IDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/display/motivo_abandono")
    public ResponseEntity<?> MostrarResumenMotivosAbandono() {

        //Total
        Set<Data> data_list =
                dataService.MostrarData();
        int total_data = data_list.size();

        //Factor Economico
        Set<Data> data_list_factor_economico =
                dataService.BuscarData_By_MotivoAbandono("Economía");
        int factor_economico = data_list_factor_economico.size();
        double porcentaje_fe = ((double) factor_economico / (double) total_data) * 100;

        //Factor Psicologico
        Set<Data> data_list_factor_psicologico =
                dataService.BuscarData_By_MotivoAbandono("Psicológico");
        int factor_psicologico = data_list_factor_psicologico.size();
        double porcentaje_p = ((double) factor_psicologico / (double) total_data) * 100;

        //Factor Tecnologico
        Set<Data> data_list_factor_tecnologico =
                dataService.BuscarData_By_MotivoAbandono("Infraestructura");
        int factor_tecnologico = data_list_factor_tecnologico.size();
        double porcentaje_t = ((double) factor_tecnologico / (double) total_data) * 100;

        //Factor Responsabilidad Familiar
        Set<Data> data_list_factor_responsabilidadfamiliar =
                dataService.BuscarData_By_MotivoAbandono("Responsabilidad Familiar");
        int factor_responsabilidadfamiliar = data_list_factor_responsabilidadfamiliar.size();
        double porcentaje_rf = ((double) factor_responsabilidadfamiliar / (double) total_data) * 100;

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        MotivoAbandonoResponse motivoAbandonoResponse = new MotivoAbandonoResponse(
                nf.format(porcentaje_fe),
                nf.format(porcentaje_p),
                nf.format(porcentaje_t),
                nf.format(porcentaje_rf)
        );

        return new ResponseEntity<>(motivoAbandonoResponse, HttpStatus.OK);
    }
}
