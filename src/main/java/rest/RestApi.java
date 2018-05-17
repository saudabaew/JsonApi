package rest;

import rest.request.SensorOfPeriodInner;
import rest.response.SensorOfPeriodList;
import rest.dto.SensorOfPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RestApi {

    @Autowired
    private JdbcTemplate jdbcTemplateAgrotronic;

    @RequestMapping(value = "/idunit/idsensor", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    private HttpEntity<SensorOfPeriodList> sensorValueOfPeriod(@RequestBody SensorOfPeriodInner sensorOfPeriodInner){
        int idUnit = sensorOfPeriodInner.getIdUnit();
        int idSensor = sensorOfPeriodInner.getIdSensor();
        LocalDateTime from = sensorOfPeriodInner.getFrom();
        LocalDateTime to = sensorOfPeriodInner.getTo();

        List<SensorOfPeriod> sensorListOfPeriod = jdbcTemplateAgrotronic.
                query("SELECT * FROM public.get_sensor (?,?,?,?)",
                new RowMapper<SensorOfPeriod>() {
                    @Override
                    public SensorOfPeriod mapRow(ResultSet rs, int rowNum) throws SQLException {
                        SensorOfPeriod sensor = new SensorOfPeriod();
                        sensor.setDateTime(rs.getTimestamp("date_s").toLocalDateTime());
                        sensor.setName(rs.getString("param_name"));
                        sensor.setValue(rs.getString("value"));
                        return sensor;
                    }
                }, idUnit, idSensor, from, to);
        return new ResponseEntity<>(new SensorOfPeriodList(sensorListOfPeriod), HttpStatus.OK);
    }

    //тестовый метод значений датчиков за период
    @RequestMapping(value = "/idunit/idsensor")
    private HttpEntity<SensorOfPeriodList> sensorValueOfPeriodGet(){
        int idUnit = 273;
        int idSensor = 53812;
        LocalDateTime from = LocalDateTime.of(2013, 6, 13, 8, 11, 1);
        LocalDateTime to = LocalDateTime.of(2017, 8, 13, 13, 10, 30);

        List<SensorOfPeriod> sensorListOfPeriod = jdbcTemplateAgrotronic.
                query("SELECT * FROM public.get_sensor (?,?,?,?)",
                new RowMapper<SensorOfPeriod>() {
                    @Override
                    public SensorOfPeriod mapRow(ResultSet rs, int rowNum) throws SQLException {
                        SensorOfPeriod sensor = new SensorOfPeriod();
                        sensor.setDateTime(rs.getTimestamp("date_s").toLocalDateTime());
                        sensor.setName(rs.getString("param_name"));
                        sensor.setValue(rs.getString("value"));
                        return sensor;
                    }
                }, idUnit, idSensor, from, to);
        return new ResponseEntity<>(new SensorOfPeriodList(sensorListOfPeriod), HttpStatus.OK);
    }

}
