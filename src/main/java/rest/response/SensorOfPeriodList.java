package rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import rest.dto.SensorOfPeriod;

import java.util.List;

@Data
@AllArgsConstructor
public class SensorOfPeriodList {
    private List<SensorOfPeriod> sensorOfPeriodOutList;
}
