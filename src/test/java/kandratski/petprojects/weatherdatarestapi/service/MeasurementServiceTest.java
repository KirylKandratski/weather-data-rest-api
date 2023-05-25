package kandratski.petprojects.weatherdatarestapi.service;

import kandratski.petprojects.weatherdatarestapi.dto.MeasurementDto;
import kandratski.petprojects.weatherdatarestapi.dto.SensorDto;
import kandratski.petprojects.weatherdatarestapi.entity.Measurement;
import kandratski.petprojects.weatherdatarestapi.entity.Sensor;
import kandratski.petprojects.weatherdatarestapi.exception.MeasurementNotAddedException;
import kandratski.petprojects.weatherdatarestapi.mapper.MeasurementMapper;
import kandratski.petprojects.weatherdatarestapi.repository.MeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MeasurementServiceTest {

    private static final String RAINY_DAYS_MESSAGE_FORMAT = "Number of rainy days - %s days.";

    private MeasurementRepository measurementRepository;
    private SensorService sensorService;
    private MeasurementMapper measurementMapper;
    private MeasurementService measurementService;

    @BeforeEach
    void setup() {
        measurementRepository = Mockito.mock(MeasurementRepository.class);
        sensorService = Mockito.mock(SensorService.class);
        measurementMapper = Mockito.mock(MeasurementMapper.class);
        measurementService = new MeasurementService(measurementRepository, sensorService, measurementMapper);
    }

    @Test
    void getAllMeasurements_MeasurementsExist_ReturnsListMeasurementDto() {
        //Arrange
        Measurement measurement1 = new Measurement();
        Measurement measurement2 = new Measurement();
        List<Measurement> measurementList = Arrays.asList(measurement1, measurement2);

        MeasurementDto measurementDto1 = new MeasurementDto();
        MeasurementDto measurementDto2 = new MeasurementDto();
        List<MeasurementDto> measurementDtoList = Arrays.asList(measurementDto1, measurementDto2);

        when(measurementRepository.findAll()).thenReturn(measurementList);
        when(measurementMapper.toDto(measurement1)).thenReturn(measurementDto1);
        when(measurementMapper.toDto(measurement2)).thenReturn(measurementDto2);

        //Act
        List<MeasurementDto> result = measurementService.getAllMeasurements();

        //Assert
        assertEquals(result, measurementDtoList);

    }

    @Test
    void getAllMeasurements_NoMeasurementsExist_ReturnsEmptyList() {
        //Arrange
        when(measurementRepository.findAll()).thenReturn(Collections.emptyList());

        //Act
        List<MeasurementDto> result = measurementService.getAllMeasurements();

        //Assert
        assertEquals(result, Collections.emptyList());

    }

    @Test
    void save_SensorExists_SavesMeasurement() {
        // Arrange
        SensorDto sensorDto = new SensorDto();
        sensorDto.setName("testSensor");
        String sensorDtoName = sensorDto.getName();
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setSensor(sensorDto);

        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor();

        when(sensorService.getOneSensorByName(sensorDtoName)).thenReturn(Optional.of(sensor));
        when(measurementMapper.toEntity(measurementDto)).thenReturn(measurement);

        // Act
        measurementService.save(measurementDto);

        // Assert
        verify(measurementRepository).save(measurement);
    }

    @Test
    void save_NoSensorExists_ThrowsMeasurementNotAddedException() {
        // Arrange
        MeasurementDto measurementDto = new MeasurementDto();
        SensorDto sensorDto = new SensorDto();
        sensorDto.setName("noSensorExist");
        measurementDto.setSensor(sensorDto);
        String sensorDtoName = sensorDto.getName();

        when(sensorService.getOneSensorByName(sensorDtoName)).thenReturn(Optional.empty());

        // Act and Assert
        MeasurementNotAddedException exception = assertThrows(MeasurementNotAddedException.class, () -> measurementService.save(measurementDto));
        assertTrue(exception.getMessage().contains("The current sensor is not registered!"));

    }


    @Test
    void countRainyDays_RainyDaysExist_ReturnsCountOfRainyDays() {
        //Arrange
        Long rainyDays = 9L;
        when(measurementRepository.countByRainingTrue()).thenReturn(rainyDays);
        String expectedMessage = String.format(RAINY_DAYS_MESSAGE_FORMAT, rainyDays);

        //Act
        String result = measurementService.countRainyDays();

        //Assert
        assertEquals(expectedMessage, result);

    }

    @Test
    void countRainyDays_NoRainyDaysExist_ReturnsZero() {
        //Arrange
        Long rainyDays = 0L;
        when(measurementRepository.countByRainingTrue()).thenReturn(rainyDays);
        String expectedMessage = String.format(RAINY_DAYS_MESSAGE_FORMAT, rainyDays);

        //Act
        String result = measurementService.countRainyDays();

        //Assert
        assertEquals(expectedMessage, result);

    }
}