package uz.edu.ai.api.one_id_api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OneIdResponseToken {

    private long expires_in;
    private String access_token;
    private String refresh_token;
    private String scope;
}
