package org.example.coremicroservice.data.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.coremicroservice.data.constant.Role;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationChangeEvent {

    private UUID clientId;

    private String username;

    private String password;

    private String mail;

    private Set<Role> roles;

}

