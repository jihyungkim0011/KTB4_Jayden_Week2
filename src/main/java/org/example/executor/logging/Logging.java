package org.example.executor.logging;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
    private static final Logger logger = Logger.getLogger("ExLogger");

    public static void printLog(String threadName, String userName, Long accountId, String accountName, String methodName, BigDecimal amount) {
        logger.info(String.format("[%10s][%10s][%10s][%10s][%10s][%s]", threadName, userName, accountId, accountName, methodName, amount));
    }

    public static void setupLogger() {
        try {
            logger.setUseParentHandlers(false);

            new File("./logs").mkdir();
            FileHandler fileHandler = new FileHandler("./logs/app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            System.err.println("로거 초기화 실패: " + e.getMessage());
        }

    }
}
