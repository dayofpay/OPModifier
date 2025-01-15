package org.vdevs.opmodifier.Handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogHandler {

    private static final String LOG_FILE_NAME = "./plugins/OPModifier/log.txt";

    static {
        initializeLogFile();
    }

    private static void initializeLogFile() {
        try {
            File logFile = new File(LOG_FILE_NAME);
            if (logFile.createNewFile()) {
                System.out.println("[OPModifier] Log file created: " + logFile.getName());
            } else {
                System.out.println("[OPModifier] Log file already exists.");
            }
        } catch (IOException e) {
            System.err.println("[OPModifier] Error initializing log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void logData(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE_NAME, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.err.println("[OPModifier] Error writing to log file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
