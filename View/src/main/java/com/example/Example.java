package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example {
    private static final Logger logger = LoggerFactory.getLogger(Example.class);

    public static void main(String[] args) {
        logger.info("Informacja logu.");
        logger.debug("Log debugowania.");
        logger.error("Log błędu.");
    }
}
