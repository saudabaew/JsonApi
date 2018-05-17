package rest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SensorOfPeriod {
    private LocalDateTime dateTime;
    private String name;
    private String value;

    public SensorOfPeriod() {
    }
}
