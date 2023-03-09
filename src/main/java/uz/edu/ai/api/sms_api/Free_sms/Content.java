package uz.edu.ai.api.sms_api.Free_sms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@NoArgsConstructor
public class Content {
	private Integer code = ThreadLocalRandom.current().nextInt(99999, 1000000);
	private String text = "taklif.edu.uz tasdiqlash kodi:" + code;
}
