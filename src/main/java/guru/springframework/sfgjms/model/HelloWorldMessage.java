package guru.springframework.sfgjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * HelloWorldMessage
 * Created by Vincenzo
 * on the 01/02/2020 18:16
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {

    static final long serialVersionUID = 42L;

    private UUID id;
    private String message;

}
