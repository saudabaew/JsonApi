package rest.request;

import lombok.Data;

@Data
public class User {
    private String login;
    private String password;
}
