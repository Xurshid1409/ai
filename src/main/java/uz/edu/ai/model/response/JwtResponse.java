package uz.edu.ai.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private Integer id;
    private String username;
    private String jwtToken;
    private String role;
}
