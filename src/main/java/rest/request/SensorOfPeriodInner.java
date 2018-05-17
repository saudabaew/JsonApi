package rest.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SensorOfPeriodInner {
    private int idUnit;
    private int idSensor;
    private LocalDateTime from;
    private LocalDateTime to;
}
