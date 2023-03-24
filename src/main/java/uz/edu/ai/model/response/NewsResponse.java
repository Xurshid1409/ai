package uz.edu.ai.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.edu.ai.domain.News;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {

    private Integer id;
    private String titleUZ;
    private String titleRU;
    private String titleEN;
    private String textUZ;
    private String textRU;
    private String textEN;
    private String createdDate;
    private Boolean isPublic;
    List<DocumentResponse> documentResponses = new ArrayList<>();
    public NewsResponse(News news, List<DocumentResponse> documentResponses) {
        this.id = news.getId();
        this.titleUZ = news.getTitleUZ();
        this.titleRU = news.getTitleRU();
        this.titleEN = news.getTitleEN();
        this.textUZ = news.getTextUZ();
        this.textRU = news.getTextRU();
        this.textEN = news.getTextEN();
        this.createdDate = news.getNews_date();
        this.isPublic = news.getIsPublic();
        this.documentResponses = documentResponses;
    }
}
