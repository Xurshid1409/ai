package uz.edu.ai.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.edu.ai.domain.Offer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferResponse {

    private Integer id;
    private String phoneNumber;
    private String email;
    private String fullName;
    private String text;
    private String answer;

    public OfferResponse(Offer offer) {

        this.id = offer.getId();
        this.phoneNumber = offer.getPhoneNumber();
        this.email = offer.getEmail();
        this.fullName = offer.getFullName();
        this.text = offer.getText();
        if (offer.getAnswer() != null)
        this.answer = offer.getAnswer();
    }
}
