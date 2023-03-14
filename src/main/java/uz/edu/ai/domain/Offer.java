package uz.edu.ai.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "offer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Offer extends AbstractEntity { //Murojaat

    private String phoneNumber;
    private String email;
    private String fullName;
    @Column(length = 8192)
    private String text;

    @Column(length = 8192)
    private String answer;
    private String status = "Yangi";
}
