package org.example.coremicroservice.service.converter;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.example.coremicroservice.data.constant.Role;
import org.example.coremicroservice.data.model.UserCredential;
import org.example.coremicroservice.dto.request.UserCredentialRequestDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-24T17:04:00+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Microsoft)"
)
@Component
public class UserCredentialMapperImpl implements UserCredentialMapper {

    @Override
    public UserCredential toEntity(UserCredentialRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserCredential userCredential = new UserCredential();

        userCredential.setClientId( dto.getClientId() );
        userCredential.setUsername( dto.getUsername() );
        userCredential.setMail( dto.getMail() );
        userCredential.setPassword( dto.getPassword() );
        Set<Role> set = dto.getRoles();
        if ( set != null ) {
            userCredential.setRoles( new LinkedHashSet<Role>( set ) );
        }

        return userCredential;
    }
}
