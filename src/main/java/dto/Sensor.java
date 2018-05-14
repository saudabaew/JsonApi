package dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Sensor {
    private String name;
    private String value;
    private LocalDate date;
}
