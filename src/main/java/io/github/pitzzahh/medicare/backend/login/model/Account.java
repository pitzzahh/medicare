package io.github.pitzzahh.medicare.backend.login.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Data;

@Data
@ToString
@EqualsAndHashCode
public class Account {
    private String username;
    private String password;
}
