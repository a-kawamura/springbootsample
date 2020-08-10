package mrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication

// In case to make jar
//public class MrsApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(MrsApplication.class, args);
//	}

// In case to make war ↓
public class MrsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MrsApplication.class, args);
	}

}
