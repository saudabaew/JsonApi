package dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class RestApi {

    @Autowired
    private JdbcTemplate jdbcTemplateAgrotronic;

    @RequestMapping(value = "/sensor/imei", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    private HttpEntity<SensorList> sensorListHttpEntity(@RequestBody SensorInner sensorInner){
        long imei = sensorInner.getImei();
        LocalDate from = sensorInner.getFrom();
        List<Sensor> sensorList = jdbcTemplateAgrotronic.
                query("SELECT date, param_name, value FROM public.sensor_history WHERE imei = ? AND date > ? ORDER BY date",
                new RowMapper<Sensor>() {
                    @Override
                    public Sensor mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Sensor sensor = new Sensor();
                        sensor.setName(rs.getString("param_name"));
                        sensor.setValue(rs.getString("value"));
                        sensor.setDate(rs.getDate("date").toLocalDate());
                        return sensor;
                    }
                }, imei, from);

        return new ResponseEntity<>(new SensorList(sensorList), HttpStatus.OK);
    }
}
