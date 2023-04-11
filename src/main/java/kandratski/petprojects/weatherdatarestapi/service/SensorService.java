package kandratski.petprojects.weatherdatarestapi.service;

import kandratski.petprojects.weatherdatarestapi.dto.SensorDTO;
import kandratski.petprojects.weatherdatarestapi.entity.Sensor;
import kandratski.petprojects.weatherdatarestapi.repository.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    public SensorService(SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }

    public List<SensorDTO> getAllSensors() {
        return sensorRepository.findAll()
                .stream()
                .map(sensor -> modelMapper.map(sensor, SensorDTO.class))
                .collect(Collectors.toList());
    }

    public SensorDTO getOneSensorById(Integer id) {
        Optional<Sensor> sensorRepositoryById = sensorRepository.findById(id);
        Sensor sensor = sensorRepositoryById.orElse(null);

        return modelMapper.map(sensor, SensorDTO.class);
    }

    public Sensor getOneSensorByName(String name) {
        return sensorRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void save(SensorDTO sensorDTO) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        sensorRepository.save(sensor);
    }
}
