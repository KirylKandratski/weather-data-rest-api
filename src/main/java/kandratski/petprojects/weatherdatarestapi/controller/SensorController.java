package kandratski.petprojects.weatherdatarestapi.controller;

import jakarta.validation.Valid;
import kandratski.petprojects.weatherdatarestapi.dto.SensorDTO;
import kandratski.petprojects.weatherdatarestapi.service.SensorService;
import kandratski.petprojects.weatherdatarestapi.exception.SensorErrorResponse;
import kandratski.petprojects.weatherdatarestapi.exception.SensorNotCreatedException;
import kandratski.petprojects.weatherdatarestapi.exception.SensorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public ResponseEntity<HttpStatus> registrationSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(errorMsg.toString());

        } else {
            sensorService.save(sensorDTO);

            return ResponseEntity.ok(HttpStatus.OK);
        }


    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException sensorNotFoundException) {
        SensorErrorResponse response = new SensorErrorResponse("Sensor with this id wasn't found!");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException sensorNotCreatedException) {
        SensorErrorResponse response = new SensorErrorResponse(sensorNotCreatedException.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
