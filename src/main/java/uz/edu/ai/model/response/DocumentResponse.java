package uz.edu.ai.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.edu.ai.domain.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {

    private Integer id;
    private String fileUrl;
    private String fileName;

    public DocumentResponse(Document document) {
        this.id = document.getId();
        this.fileUrl = document.getFileUrl();
        this.fileName = document.getFileName();
    }
}
