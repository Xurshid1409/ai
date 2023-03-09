package uz.edu.ai.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String message;
    private Boolean status;
    private Object object;

    public Result(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
