package kandratski.petprojects.weatherdatarestapi.repository;

import kandratski.petprojects.weatherdatarestapi.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    Long countByRainingTrue();
}
