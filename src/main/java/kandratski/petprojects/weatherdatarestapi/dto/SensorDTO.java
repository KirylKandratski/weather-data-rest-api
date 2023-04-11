package kandratski.petprojects.weatherdatarestapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kandratski.petprojects.weatherdatarestapi.entity.Measurement;

import java.util.List;

public class SensorDTO {

    private Integer sensor_id;

    @NotNull(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;

    private List<Measurement> measurements;

    public SensorDTO() {
    }

    public Integer getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(Integer sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
               "sensor_id=" + sensor_id +
               ", name='" + name + '\'' +
               ", measurements=" + measurements +
               '}';
    }
}
