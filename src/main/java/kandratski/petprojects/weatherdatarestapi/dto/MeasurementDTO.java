package kandratski.petprojects.weatherdatarestapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kandratski.petprojects.weatherdatarestapi.entity.Sensor;

public class MeasurementDTO {

    private Integer measurement_id;

    @NotNull(message = "Value should not be null")
    @Min(value = -100, message = "Value must be greater than -100")
    @Max(value = 100, message = "Value must be less than 100")
    private Float value;

    @NotNull(message = "Raining should not be null")
    private Boolean raining;

    private SensorDTO sensorDTO;

    public MeasurementDTO() {
    }

    public Integer getMeasurement_id() {
        return measurement_id;
    }

    public void setMeasurement_id(Integer measurement_id) {
        this.measurement_id = measurement_id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public void setSensorDTO(SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
               "measurement_id=" + measurement_id +
               ", value=" + value +
               ", raining=" + raining +
               ", sensorDTO=" + sensorDTO +
               '}';
    }
}
