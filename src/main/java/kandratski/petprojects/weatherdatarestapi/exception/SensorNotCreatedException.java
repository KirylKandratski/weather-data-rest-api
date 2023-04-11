package kandratski.petprojects.weatherdatarestapi.exception;

public class SensorNotCreatedException extends RuntimeException {
    public SensorNotCreatedException(String msg) {
        super(msg);
    }
}
