package kandratski.petprojects.weatherdatarestapi.service;

import kandratski.petprojects.weatherdatarestapi.dto.MeasurementDto;
import kandratski.petprojects.weatherdatarestapi.entity.Measurement;
import kandratski.petprojects.weatherdatarestapi.entity.Sensor;
import kandratski.petprojects.weatherdatarestapi.exception.MeasurementNotAddedException;
import kandratski.petprojects.weatherdatarestapi.mapper.MeasurementMapper;
import kandratski.petprojects.weatherdatarestapi.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;
    private final MeasurementMapper measurementMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService, MeasurementMapper measurementMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
        this.measurementMapper = measurementMapper;
    }

    public List<MeasurementDto> getAllMeasurements() {
        return measurementRepository.findAll()
                .stream()
                .map(measurement -> measurementMapper.toDto(measurement))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(MeasurementDto measurementDTO) {

        String nameSensor = measurementDTO.getSensor().getName();
        Optional<Sensor> sensorByName = sensorService.getOneSensorByName(nameSensor);

        sensorByName.ifPresentOrElse(
                sensor -> {
                    Measurement measurement = measurementMapper.toEntity(measurementDTO);
                    measurement.setSensor(sensor);
                    measurement.setRecordingTime(LocalDateTime.now());

                    measurementRepository.save(measurement);
                },
                () -> {
                    throw new MeasurementNotAddedException("The current sensor is not registered!");
                }
        );

    }

    public String rainyDaysCount() {


        int rainyDays = (int) measurementRepository.findAll()
                .stream()
                .filter(measurement -> measurement.getRaining().equals(true))
                .count();

        return "Number of rainy days - " + rainyDays + " days.";
    }
}
