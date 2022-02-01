package sep.webshopback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.validation.Valid;

@SpringBootApplication
@EnableFeignClients
public class WebShopBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebShopBackApplication.class, args);
    }

}
