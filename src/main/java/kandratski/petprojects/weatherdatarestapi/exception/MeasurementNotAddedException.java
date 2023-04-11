package kandratski.petprojects.weatherdatarestapi.exception;

public class MeasurementNotAddedException extends RuntimeException {
    public MeasurementNotAddedException(String msg) {
        super(msg);
    }
}
