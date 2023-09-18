package cm.Bangoulap.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseEntity {
    private int code;
    private String message;
    private String status;
    private Object value;
}
