package uz.edu.ai.domain;

import jakarta.persistence.*;
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
public class News extends AbstractEntity {

    @Column(length = 2048)
    private String titleUZ;
    @Column(length = 2048)
    private String titleRU;
    @Column(length = 2048)
    private String titleEN;
    @Column(length = 2048)
    private String anonsUZ;
    @Column(length = 2048)
    private String anonsRU;
    @Column(length = 2048)
    private String anonsEN;
    @Column(length = 500000)
    private String textUZ;
    @Column(length = 500000)
    private String textRU;
    @Column(length = 500000)
    private String textEN;
    private String news_date;

    @Column(length = 2048)
    private String url;
    private Boolean isPublic = Boolean.FALSE;

    @OneToMany(mappedBy = "news")
    private List<Document> documents=new ArrayList<>();

}
