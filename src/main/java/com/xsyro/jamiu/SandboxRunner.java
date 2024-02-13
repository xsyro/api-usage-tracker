package com.xsyro.jamiu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SandboxRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("here");
    }
}
