package pojo.answers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetAnswer {

    private int code;
    private String type;
    private String message;

}
