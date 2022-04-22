package tw.com.fcb.fp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FcbCoreFPApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcbCoreFPApplication.class, args);
	}

}
