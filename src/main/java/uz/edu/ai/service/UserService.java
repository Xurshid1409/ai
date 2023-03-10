package uz.edu.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.edu.ai.api.one_id_api.OneIdResponseToken;
import uz.edu.ai.api.one_id_api.OneIdResponseUserInfo;
import uz.edu.ai.api.one_id_api.OneIdServiceApi;
import uz.edu.ai.constants.DefaultRole;
import uz.edu.ai.constants.ResponseMessage;
import uz.edu.ai.domain.Role;
import uz.edu.ai.domain.User;
import uz.edu.ai.model.Result;
import uz.edu.ai.model.response.JwtResponse;
import uz.edu.ai.repository.RoleRepository;
import uz.edu.ai.repository.UserRepository;
import uz.edu.ai.security.JwtTokenProvider;
import uz.edu.ai.security.UserDetailsImpl;
import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final OneIdServiceApi oneIdServiceApi;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public URI redirectOneIdUrl() {
        return oneIdServiceApi.redirectOneIdUrl();
    }


    @Transactional
    public Result signIn(String code) {
        try {
            OneIdResponseToken oneIdToken = oneIdServiceApi.getAccessAndRefreshToken(code);
            if (oneIdToken == null) {
                return new Result("Token" + ResponseMessage.NOT_FOUND.getMessage(), false);
            }
            OneIdResponseUserInfo oneIdUserInfo = oneIdServiceApi.getUserInfo(oneIdToken.getAccess_token());
            Optional<User> user = userRepository.findUserByPhoneNumberOrPinfl(oneIdUserInfo.getMobPhoneNo(), oneIdUserInfo.getPin());
            if (user.isPresent() && user.get().getPassword() == null) {
                User userNew = user.get();
                userNew.setGender(oneIdUserInfo.getGd());
                userNew.setFullName(oneIdUserInfo.getFullName());
                userNew.setPermanentAddress(oneIdUserInfo.getPerAdr());
                userNew.setGivenDate(oneIdUserInfo.getPportIssueDate());
                userNew.setBirthDate(oneIdUserInfo.getBirthDate());
                userNew.setPhoneNumber(oneIdUserInfo.getMobPhoneNo());
                userNew.setCitizenship(oneIdUserInfo.getCtzn());
                userNew.setNationality(oneIdUserInfo.getNatn());
                Role role = roleRepository.findByName(DefaultRole.ROLE_ADMIN.getMessage()).get();
                userNew.setRole(role);
                userNew.setPassword(passwordEncoder.encode(userNew.getPinfl() + userNew.getSerialAndNumber()));
                userRepository.save(userNew);
                JwtResponse jwtResponse = getJwtResponse(userNew.getPhoneNumber(), userNew.getPinfl() + userNew.getSerialAndNumber());
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, jwtResponse);
            } else if (user.isPresent()) {
                JwtResponse jwtResponse = getJwtResponse(user.get().getPhoneNumber(), user.get().getPinfl() + user.get().getSerialAndNumber());
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, jwtResponse);
            }
            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
        }
    }

    @Transactional
    public JwtResponse getJwtResponse(String username, String password) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        String jwtToken = jwtTokenProvider.generateJWTToken(userDetails);
        String token = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().get();
        return new JwtResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                jwtToken,
                token
        );
    }
}
