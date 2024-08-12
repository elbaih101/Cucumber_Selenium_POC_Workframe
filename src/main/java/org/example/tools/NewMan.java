package org.example.tools;

import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * this class handles newman scripts by implemnting a process builder to run a local command on the machine
 */
public class NewMan {

    static String userDirectory = System.getProperty("user.home");

    public static void runPostmanTestCase(String postman_collection_Path) {
        try {
            // Define the command to run Newman
            String[] command = {
                    userDirectory + "\\AppData\\Roaming\\npm\\newman.cmd",
                    "run",
                    postman_collection_Path, // Replace with the path to your Postman collection file
                    "--reporters",
                    "cli"
            };

            // Start the process
            Process process = new ProcessBuilder(command).start();

            // Read the output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Check if the process completed successfully
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException(postman_collection_Path + " failed with exit code: " + exitCode);
            }

        } catch (Exception e) {
            LoggerFactory.getLogger(NewMan.class).error("Failed to execute Postman test case", e);
        }
    }



    public static void checkNewmanInstalled()throws RuntimeException , IOException,InterruptedException {
        // Create the command
        ProcessBuilder processBuilder = new ProcessBuilder("newman", "-v");

        // Start the process
        Process process = processBuilder.start();

        // Capture the output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // Capture errors
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            System.err.println(errorLine);
        }

        // Wait for the process to complete
        int exitCode = process.waitFor();
        System.out.println("Exited with code: " + exitCode);

        if (exitCode != 0) {
            throw new RuntimeException("Newman is not installed or not found in PATH.");
        } else {
            System.out.println("Newman is installed.");
        }


    }
}

