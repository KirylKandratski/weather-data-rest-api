package kandratski.petprojects.weatherdatarestapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {

    private Integer measurementId;

    @NotNull(message = "Value should not be null")
    @Min(value = -100, message = "Value must be greater than -100")
    @Max(value = 100, message = "Value must be less than 100")
    private Float value;

    @NotNull(message = "Raining should not be null")
    private Boolean raining;

    @NotNull(message = "Sensor should not be null")
    private SensorDTO sensor;


    public MeasurementDTO() {
    }

    public Integer getMeasurement_id() {
        return measurementId;
    }

    public void setMeasurement_id(Integer measurement_id) {
        this.measurementId = measurement_id;
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

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
               "measurement_id=" + measurementId +
               ", value=" + value +
               ", raining=" + raining +
               ", sensor=" + sensor +
               '}';
    }
}
