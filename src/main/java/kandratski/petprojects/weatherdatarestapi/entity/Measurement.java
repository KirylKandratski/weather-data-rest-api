package kandratski.petprojects.weatherdatarestapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurements")
@Data
@NoArgsConstructor
public class Measurement {

    @Id
    @Column(name = "measurement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value", nullable = false)
    @NotNull(message = "Value should not be null")
    @Min(value = -100, message = "Value must be greater than -100")
    @Max(value = 100, message = "Value must be less than 100")
    private Float value;

    @Column(name = "raining", nullable = false)
    @NotNull(message = "Raining should not be null")
    private Boolean raining;

    @Column(name = "recording_time", nullable = false)
    private LocalDateTime recordingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;
}
