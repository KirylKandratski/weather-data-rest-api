package kandratski.petprojects.weatherdatarestapi.mapper;

import kandratski.petprojects.weatherdatarestapi.dto.MeasurementDto;
import kandratski.petprojects.weatherdatarestapi.entity.Measurement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasurementMapper {

    MeasurementDto toDto(Measurement measurement);

    Measurement toEntity(MeasurementDto measurementDTO);

}
