package kandratski.petprojects.weatherdatarestapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeasurementDto {

    private Integer id;

    @NotNull(message = "Value should not be null")
    @Min(value = -100, message = "Value must be greater than -100")
    @Max(value = 100, message = "Value must be less than 100")
    private Float value;

    @NotNull(message = "Raining should not be null")
    private Boolean raining;

    @NotNull(message = "Sensor should not be null")
    private SensorDto sensor;
}
