package ch.baloise.sarahtar.sensortrigger;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SensorTriggerApplication {

    private static final int PIN_BUTTON = 24; // PIN 18 = BCM 24

    private static int pressCount = 0;

    public static void main(String[] args) {
        SpringApplication.run(SensorTriggerApplication.class, args);

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
                pressCount++;
                console.println("Button was pressed for the " + pressCount + "th time");
            }
        });

    }

}
