package atangle.iot;

import org.babyfish.jimmer.client.EnableImplicitApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.atangle.*", "atangle.*"})
@EnableImplicitApi
public class IotApp {
    public static void main(String[] args) {
        SpringApplication.run(IotApp.class, args);
    }

}
