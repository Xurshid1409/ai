package uz.edu.ai.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.edu.ai.domain.Member;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private Integer id;
    private String fullName;
    private String workPlace;
    List<DocumentResponse> documentResponses = new ArrayList<>();
    public MemberResponse(Member member, List<DocumentResponse> documentResponses) {
        this.id = member.getId();
        this.fullName = member.getFullName();
        this.workPlace = member.getWorkPlace();
        this.documentResponses = documentResponses;
    }
}
