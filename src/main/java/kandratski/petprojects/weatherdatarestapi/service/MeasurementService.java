package kandratski.petprojects.weatherdatarestapi.service;

import kandratski.petprojects.weatherdatarestapi.dto.MeasurementDTO;
import kandratski.petprojects.weatherdatarestapi.entity.Measurement;
import kandratski.petprojects.weatherdatarestapi.repository.MeasurementRepository;
import kandratski.petprojects.weatherdatarestapi.util.MeasurementNotAddedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

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
        if (sensorService.getOneSensorByName(nameSensor).isEmpty()) {
            throw new MeasurementNotAddedException("The current sensor is not registered!");
        } else {
            Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
            measurement.setSensor(sensorService.getOneSensorByName(nameSensor).get());
            measurement.setRecordingTime(LocalDateTime.now());

            measurementRepository.save(measurement);
        }

    }

    public String rainyDaysCount() {


        int rainyDays = (int) measurementRepository.findAll()
                .stream()
                .filter(measurement -> measurement.getRaining().equals(true))
                .count();

        return "Number of rainy days - " + rainyDays + " days.";
    }
}
