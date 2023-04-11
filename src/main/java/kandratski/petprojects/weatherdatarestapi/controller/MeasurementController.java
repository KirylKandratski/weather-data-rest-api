package kandratski.petprojects.weatherdatarestapi.controller;

import jakarta.validation.Valid;
import kandratski.petprojects.weatherdatarestapi.dto.MeasurementDTO;
import kandratski.petprojects.weatherdatarestapi.service.MeasurementService;
import kandratski.petprojects.weatherdatarestapi.util.MeasurementErrorResponse;
import kandratski.petprojects.weatherdatarestapi.util.MeasurementNotAddedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping()
    public List<MeasurementDTO> getAllMeasurements() {
        return measurementService.getAllMeasurements();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotAddedException(errorMsg.toString());

        } else {
            measurementService.save(measurementDTO);

            return ResponseEntity.ok(HttpStatus.OK);
        }


    }

    @GetMapping("/rainyDaysCount")
    public String rainyDaysCount() {
        return measurementService.rainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddedException exception) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
