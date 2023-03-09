package uz.edu.ai.api.sms_api.Free_sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessagesItem {
    private String recipient;
    private SmsFree sms = new SmsFree();
    @JsonProperty("message-id")
    private String messageId = "abc000000001";

    public MessagesItem(String phoneNumber) {
        this.recipient = phoneNumber;
    }


}
