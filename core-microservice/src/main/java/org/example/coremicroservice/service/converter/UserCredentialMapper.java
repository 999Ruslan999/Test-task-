package org.example.coremicroservice.service.converter;

import org.example.coremicroservice.data.model.UserCredential;
import org.example.coremicroservice.dto.request.UserCredentialRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCredentialMapper {

    UserCredential toEntity(UserCredentialRequestDto dto);

}
