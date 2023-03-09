package uz.edu.ai.api.sms_api.Free_sms;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import uz.edu.ai.constants.ApiConstant;

@Service
@RequiredArgsConstructor
public class FreeSmsService {
    private final WebClient webClient;

    public void sendSms(SmsFreeRequest request) {
        try {
            Thread.sleep(1000);
            this.webClient.post()
                    .uri(ApiConstant.SMS_FREE_URL)
                    .headers(httpHeader -> httpHeader.setBasicAuth(ApiConstant.SMS_FREE_LOGIN, ApiConstant.SMS_FREE_PASSWORD))
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
