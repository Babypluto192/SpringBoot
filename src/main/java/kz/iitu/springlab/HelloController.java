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
        int min = 999999999;
        int max = 0;
        float avg = 0;
        for(int i =0; i < stringArray.length; i++) {
            var currentNumber = Integer.parseInt(stringArray[i]);
            if (currentNumber < min) {
                min = currentNumber;
            }
            if(currentNumber > max) {
                max = currentNumber;
            }

            avg += currentNumber;
        }
        avg = avg / stringArray.length;

        return new Stats(400, min,max,avg);
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }

    public record Info(String owner, String javaVersion, int cpuCores) { }

    public record Stats(int status, int min, int max, float avg) { }
}
