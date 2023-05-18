package kandratski.petprojects.weatherdatarestapi.service;

import kandratski.petprojects.weatherdatarestapi.dto.SensorDto;
import kandratski.petprojects.weatherdatarestapi.entity.Sensor;
import kandratski.petprojects.weatherdatarestapi.exception.SensorNotCreatedException;
import kandratski.petprojects.weatherdatarestapi.exception.SensorNotFoundException;
import kandratski.petprojects.weatherdatarestapi.mapper.SensorMapper;
import kandratski.petprojects.weatherdatarestapi.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    private final SensorMapper sensorMapper;

    @Autowired
    public SensorService(SensorRepository sensorRepository, SensorMapper sensorMapper) {
        this.sensorRepository = sensorRepository;
        this.sensorMapper = sensorMapper;
    }

    public List<SensorDto> getAllSensors() {
        return sensorRepository.findAll()
                .stream()
                .map(sensor -> sensorMapper.toDto(sensor))
                .collect(Collectors.toList());
    }

    public SensorDto getOneSensorById(Integer id) {
        Optional<Sensor> sensorRepositoryById = sensorRepository.findById(id);

        if (sensorRepositoryById.isEmpty()) {
            throw new SensorNotFoundException();
        } else {
            Sensor sensor = sensorRepositoryById.get();
            return sensorMapper.toDto(sensor);
        }

    }

    public Optional<Sensor> getOneSensorByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(SensorDto sensorDTO) {

        String nameSensor = sensorDTO.getName();

        if (sensorRepository.findByName(nameSensor).isPresent()) {
            throw new SensorNotCreatedException("A sensor with the same name already exists");
        } else {
            Sensor sensor = sensorMapper.toEntity(sensorDTO);
            sensorRepository.save(sensor);
        }

    }
}
