package uz.edu.ai.api.sms_api.Free_sms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SmsFreeRequest {
    private List<MessagesItem> messages = new ArrayList<>();

}