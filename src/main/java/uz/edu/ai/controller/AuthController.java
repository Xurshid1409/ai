package uz.edu.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.edu.ai.model.Result;
import uz.edu.ai.model.request.OfferRequest;
import uz.edu.ai.service.OfferService;
import uz.edu.ai.service.UserService;
import java.net.URI;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final OfferService offerService;

    @GetMapping("/oneId")
    public ResponseEntity<?> getOneId() {
        URI uri = userService.redirectOneIdUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(uri)
                .build();
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestParam(value = "code") String code) {
        Result result = userService.signIn(code);
        return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
    }

    @PostMapping("createOffer")
    public ResponseEntity<?> createOffer(@RequestBody OfferRequest request) {
        Result result = offerService.createOffer(request);
        return ResponseEntity.status(result.getStatus() ? 201 : 400).body(result);
    }
}
