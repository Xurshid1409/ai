package uz.edu.ai.api.sms_api.Free_sms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SmsFree {
    private String originator = "3700";
    private Content content = new Content();
}
