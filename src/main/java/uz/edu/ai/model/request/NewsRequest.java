package uz.edu.ai.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {

    private String titleUZ;
    private String titleRU;
    private String titleEN;
    private String anonsUZ;
    private String anonsRU;
    private String anonsEN;
    private String textUZ;
    private String textRU;
    private String textEN;
    private String url;
    private String newsDate;
    private List<String> fileNames = new ArrayList<>();

}
