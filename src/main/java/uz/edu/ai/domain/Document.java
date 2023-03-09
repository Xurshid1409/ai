package uz.edu.ai.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document extends AbstractEntity {


    @Column(length = 2048)
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    public News news;
}
