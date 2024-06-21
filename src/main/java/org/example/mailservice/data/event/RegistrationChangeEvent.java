package org.example.mailservice.data.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mailservice.data.constant.Role;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationChangeEvent {

    private UUID clientId;

    private String username;

    private String password;

    private String mail;

    private Set<Role> roles;

}

