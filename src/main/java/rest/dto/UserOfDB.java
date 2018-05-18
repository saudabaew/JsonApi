package rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserOfDB {
    private int id;
    private String password;
    private boolean enabled;
    private LocalDateTime timestamp_edit;
    private String login;
}
