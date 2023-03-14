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
public class OfferRequest {
    private String phoneNumber;
    private String email;
    private String fullName;
    private String text;
}
