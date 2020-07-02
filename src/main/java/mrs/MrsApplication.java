package mrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MrsApplication.class, args);
	}

	// In case to make war ↓
//	public class MrsApplication extends SpringBootServletInitializer {
//
//		public static void main(String[] args) {
//			SpringApplication.run(MrsApplication.class, args);
//		}

}
