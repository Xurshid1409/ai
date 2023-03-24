package uz.edu.ai.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {

    private String titleUZ;
    private String titleRU;
    private String titleEN;
    private String textUZ;
    private String textRU;
    private String textEN;
    private String newsDate;

}
