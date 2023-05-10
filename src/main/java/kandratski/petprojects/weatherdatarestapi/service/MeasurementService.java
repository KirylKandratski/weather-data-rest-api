package kandratski.petprojects.weatherdatarestapi.service;

import kandratski.petprojects.weatherdatarestapi.dto.MeasurementDTO;
import kandratski.petprojects.weatherdatarestapi.entity.Measurement;
import kandratski.petprojects.weatherdatarestapi.entity.Sensor;
import kandratski.petprojects.weatherdatarestapi.exception.MeasurementNotAddedException;
import kandratski.petprojects.weatherdatarestapi.repository.MeasurementRepository;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, ModelMapper modelMapper, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    public List<MeasurementDTO> getAllMeasurements() {
        return measurementRepository.findAll()
                .stream()
                .map(measurement -> modelMapper.map(measurement, MeasurementDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(MeasurementDTO measurementDTO) {

        String nameSensor = measurementDTO.getSensor().getName();
        Optional<Sensor> sensorByName = sensorService.getOneSensorByName(nameSensor);

        sensorByName.ifPresentOrElse(
                sensor -> {
                    Measurement measurement = convertToMeasurement(measurementDTO);
                    measurement.setSensor(sensor);
                    measurement.setRecordingTime(LocalDateTime.now());

                    measurementRepository.save(measurement);
                },
                () -> {
                    throw new MeasurementNotAddedException("The current sensor is not registered!");
                }
        );

    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    public String rainyDaysCount() {


        int rainyDays = (int) measurementRepository.findAll()
                .stream()
                .filter(measurement -> measurement.getRaining().equals(true))
                .count();

        return "Number of rainy days - " + rainyDays + " days.";
    }
}
