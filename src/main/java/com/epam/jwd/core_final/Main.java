package com.epam.jwd.core_final;


import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Start application");
        try {
            Application.start();
        } catch (InvalidStateException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("End application");
    }
}