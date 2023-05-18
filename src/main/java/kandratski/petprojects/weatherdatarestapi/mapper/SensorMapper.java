package kandratski.petprojects.weatherdatarestapi.mapper;

import kandratski.petprojects.weatherdatarestapi.dto.SensorDto;
import kandratski.petprojects.weatherdatarestapi.entity.Sensor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorMapper {
    SensorDto toDto(Sensor sensor);

    Sensor toEntity(SensorDto sensorDto);
}
