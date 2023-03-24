package uz.edu.ai.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    private String fullName;
    private String fullNameRU;
    private String fullNameEN;
    private String workPlace;
    private String workPlaceRU;
    private String workPlaceEN;

}
