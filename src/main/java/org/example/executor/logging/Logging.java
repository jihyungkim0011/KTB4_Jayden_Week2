package org.example.executor.logging;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
    private static final Logger logger = Logger.getLogger(Logging.class.getName());

    public static void printLog(LocalDateTime dateTime, String threadName, String userName, Long accountId, String accountName, String methodName, BigDecimal amount) {
        logger.info(String.format("[%s][%s][%s][%s][%s][%s][%s]", dateTime, threadName, userName, accountId, accountName, methodName, amount));
    }

    public static void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("./logs/app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            logger.severe("로거 초기화 실패");
        }

    }
}
