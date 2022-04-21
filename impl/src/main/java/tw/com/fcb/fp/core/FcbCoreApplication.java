package tw.com.fcb.fp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FcbCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcbCoreApplication.class, args);
	}

}
