package uz.edu.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import uz.edu.ai.service.EmailService;

@SpringBootApplication
public class AiApplication {

//	public AiApplication(EmailService emailService) {
//		this.emailService = emailService;
//	}

	public static void main(String[] args) {
		SpringApplication.run(AiApplication.class, args);
	}

//	private final EmailService emailService;
//	@EventListener(ApplicationReadyEvent.class)
//	public void send() {
//		emailService.sendEmail("mukhammadjcn@gmail.com", "Test 2");
//	}
}
