package kandratski.petprojects.weatherdatarestapi.controller;

import kandratski.petprojects.weatherdatarestapi.dto.SensorDTO;
import kandratski.petprojects.weatherdatarestapi.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public List<SensorDTO> getAllSensors() {
        return sensorService.getAllSensors();
    }

    @GetMapping("/{id}")
    public SensorDTO getOneSensorById(@PathVariable("id") Integer id) {
        return sensorService.getOneSensorById(id);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registrationSensor(@RequestBody SensorDTO sensorDTO) {
        sensorService.save(sensorDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
