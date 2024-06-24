package org.example.mailservice.data.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailEvent {


    private String mail;

    @JsonCreator
    public EmailEvent(String mail) {
        this.mail = mail;
    }

}
