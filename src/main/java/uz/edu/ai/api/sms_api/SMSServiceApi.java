package uz.edu.ai.api.sms_api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import uz.edu.ai.constants.ApiConstant;


@Service
@RequiredArgsConstructor
public class SMSServiceApi {

    private final WebClient webClient;

    public void sendData(SMSApiRequest request) {
        try {
            Thread.sleep(1000);
            this.webClient.post()
                    .uri(ApiConstant.SMS_API_URL)
                    .headers(httpHeader -> httpHeader.setBasicAuth(ApiConstant.SMS_API_LOGIN, ApiConstant.SMS_API_PASSWORD))
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(void.class)
                    .block();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
