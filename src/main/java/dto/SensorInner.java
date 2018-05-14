package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SensorInner {
    private long imei;
    private LocalDate from;

    @JsonCreator
    public SensorInner(@JsonProperty("imei") long imei, @JsonProperty("date") LocalDate from) {
        this.imei = imei;
        this.from = from;
    }
}
