package uz.edu.ai.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends AbstractEntity {

    @Column(name = "full_name")
    private String fullName;

    private String pinfl;
    @Column(name = "given_date")
    private String givenDate;
    private String gender;
    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "serial_and_number", length = 15)
    private String serialAndNumber;
    @Column(name = "phone_number", length = 14)
    private String phoneNumber;
    @Column(name = "permanent_address", length = 2048)
    private String permanentAddress;
    private String citizenship;
    private String nationality;

    @Column(length = 1024)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USER_ROLE"))
    private Role role;

}
