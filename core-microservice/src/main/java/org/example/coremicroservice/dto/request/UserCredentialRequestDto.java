package org.example.coremicroservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.coremicroservice.data.constant.Role;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialRequestDto {

    private UUID clientId;

    private String username;

    private String password;

    private String mail;

    private Set<Role> roles;

}
