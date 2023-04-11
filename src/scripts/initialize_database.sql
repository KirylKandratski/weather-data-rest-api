drop table measurements;
drop table sensors;

CREATE TABLE sensors
(
    sensor_id SERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL
);

CREATE TABLE measurements
(
    measurement_id SERIAL PRIMARY KEY,
    value          REAL                     NOT NULL,
    raining        BOOLEAN                  NOT NULL,
    recording_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sensor_id      INTEGER,
    FOREIGN KEY (sensor_id) REFERENCES sensors (sensor_id) ON DELETE CASCADE
);