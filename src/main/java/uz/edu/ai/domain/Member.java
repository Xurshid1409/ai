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

@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends AbstractEntity {
    private String fullName;

    @Column(length = 4096)
    private String workPlace;

    @OneToMany(mappedBy = "members")
    private List<Document> documents=new ArrayList<>();

}
