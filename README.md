# Weather Data REST API

The Weather Data REST API project is a simple Spring Boot application that allows users to store and manage weather data
from various sensors. The application uses PostgreSQL as a database for persisting the data.

## Features

* Register new weather sensors.
* Add new weather measurements for registered sensors.
* Retrieve a list of all sensors.
* Retrieve a list of all measurements.
* Calculate the number of rainy days based on the recorded measurements.

## Installation Requirements

To install and run this project, you will need:

* Java 8 or higher
* PostgreSQL
* Maven

## Setting Up the Application 

1. Clone the repository to your local machine.

    ```
    git clone https://github.com/yourusername/weather-data-rest-api.git
    ```

2. Open the application.properties file and update the following properties with your PostgreSQL configuration:

    ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

3. Create the database schema by running the initialize_database.sql script in your PostgreSQL instance.

4. Navigate to the root directory of the project and build the application using Maven:

    ```
    mvn clean install
    ```

5. Run the application:

    ```
    java -jar target/weather-data-rest-api-0.0.1-SNAPSHOT.jar
    ```

The application will be accessible at http://localhost:8080.

## API Endpoints

1. Register a new sensor:

    ```
    POST /sensors/registration
    ```

    Example request payload:

    ```
    {
    "name": "Sensor_1"
    }
    ```

2. Add a new measurement for a registered sensor:

    ```
    POST /measurements/add
    ```

    Example request payload:

    ```
    {
    "value": 25.5,
    "raining": false,
    "sensor": {
    "name": "Sensor_1"
        }
    }
    ```

3. Get a list of all sensors:
    ```
    GET /sensors
    ```
4. Get a sensor by ID:
    ```
    GET /sensors/{id}
    ```
5. Get a list of all measurements:
    ```
    GET /measurements
    ```
6. Get the number of rainy days:
    ```
    GET /measurements/rainyDaysCount
    ```
## License

This project is licensed under the MIT License.