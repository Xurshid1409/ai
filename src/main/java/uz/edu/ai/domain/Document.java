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


    @Column(length = 4096)
    private String fileUrl;

    @Column(length = 2048)
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    public News news;

    @ManyToOne(fetch = FetchType.LAZY)
    public Member members;
}
