package kandratski.petprojects.weatherdatarestapi.repository;

import kandratski.petprojects.weatherdatarestapi.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
