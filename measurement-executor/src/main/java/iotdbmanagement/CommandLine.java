package iotdbmanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class CommandLine implements CommandLineRunner {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void run(String... args) {
        logger.info("Starting");
    }
}
