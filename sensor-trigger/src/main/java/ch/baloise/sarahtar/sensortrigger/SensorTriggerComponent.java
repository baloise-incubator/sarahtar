package ch.baloise.sarahtar.sensortrigger;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.Console;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class SensorTriggerComponent {

    private static final String RANDOM_URL = "http://localhost:8080/play/random";

    private static final int PIN_BUTTON = 27; // GPIO 27

    @PostConstruct
    public void initListeners() {
        // Create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // Print program title/header
        console.title("<!-- Sarahtar sensor-trigger started -->");

        var pi4j = Pi4J.newAutoContext();

        var buttonConfig = DigitalInput.newConfigBuilder(pi4j)
                .id("button")
                .name("Press button")
                .address(PIN_BUTTON)
                .pull(PullResistance.PULL_DOWN)
                .debounce(3000L)
                .provider("pigpio-digital-input");
        var button = pi4j.create(buttonConfig);
        button.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                console.println("Button was pressed! Calling REST Service");
                callPresenter(console);
            }
        });
    }

    private void callPresenter(Console console) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(RANDOM_URL, String.class);
        console.println(result);
    }

}
