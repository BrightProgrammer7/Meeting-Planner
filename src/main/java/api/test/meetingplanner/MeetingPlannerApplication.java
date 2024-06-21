package api.test.meetingplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@EntityScan(basePackages = "api.test.meetingplanner.entities")
//@EnableJpaRepositories(basePackages = "api.test.meetingplanner.repositories")
//@ComponentScan(basePackages = {"api.test.meetingplanner.services", "api.test.meetingplanner.controllers"})
@RestController
@SpringBootApplication
public class MeetingPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingPlannerApplication.class, args);
    }

    @GetMapping
    public String hello(){
        return "Hello Zenika 👋";
    }
}
