package kandratski.petprojects.weatherdatarestapi.util;

public class MeasurementNotAddedException extends RuntimeException {
    public MeasurementNotAddedException(String msg) {
        super(msg);
    }
}
