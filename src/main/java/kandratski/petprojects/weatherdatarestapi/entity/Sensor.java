package kandratski.petprojects.weatherdatarestapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sensors")
@NoArgsConstructor
@Data
public class Sensor {

    @Id
    @Column(name = "sensor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Measurement> measurements;
}
