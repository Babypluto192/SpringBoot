package kz.iitu.springlab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors());
    }

    @GetMapping("/stats")
    public Stats statistic(@RequestParam String numbers) {
        String[] stringArray = numbers.split(",");
        float average = 0;
        int minimum = Integer.parseInt(stringArray[0]);
        int maximum = Integer.parseInt(stringArray[0]);
        for(int i =0; i < stringArray.length; i++) {
            var currentNumber = Integer.parseInt(stringArray[i]);
            if (currentNumber < minimum) {
                minimum = currentNumber;
            }
            if(currentNumber > maximum) {
                maximum = currentNumber;
            }

            average += currentNumber;
        }
        average = average / stringArray.length;

        return new Stats(500, minimum,maximum,average);
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }

    public record Info(String owner, String javaVersion, int cpuCores) { }

    public record Stats(int status, int minimum, int maximum, float average) { }
}
