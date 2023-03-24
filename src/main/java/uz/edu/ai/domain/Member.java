package uz.edu.ai.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends AbstractEntity {
    private String fullName;
    private String fullNameRU;
    private String fullNameEN;

    @Column(length = 4096)
    private String workPlace;
    @Column(length = 4096)
    private String workPlaceRU;
    @Column(length = 4096)
    private String workPlaceEN;

    @OneToMany(mappedBy = "members")
    private List<Document> documents=new ArrayList<>();

}
