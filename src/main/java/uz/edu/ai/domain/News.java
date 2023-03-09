package uz.edu.ai.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(length = 8192)
    private String textUZ;

    @Column(length = 8192)
    private String textRU;

    @Column(length = 8192)
    private String textEN;

}
