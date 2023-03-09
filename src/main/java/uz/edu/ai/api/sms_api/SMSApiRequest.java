package uz.edu.ai.api.sms_api;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@NoArgsConstructor
public class SMSApiRequest {

    private String phone_number;
    private Integer code = ThreadLocalRandom.current().nextInt(99999, 1000000);
    private String message = " taklif.edu.uz tasdiqlash kodi" + code;

    public SMSApiRequest(String phone_number) {
        this.phone_number = phone_number;
    }
}
