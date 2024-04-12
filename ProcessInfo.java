import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessInfo {
    public static void main(String[] args) {
        try {
            // Create a ProcessBuilder with the command and its arguments
            ProcessBuilder processBuilder = new ProcessBuilder("tasklist", "/fo", "csv", "/nh");

            // Start the process
            Process process = processBuilder.start();

            // Read output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line of output
                String[] tokens = line.split("\",\"");
                if (tokens.length >= 5) {
                    String processName = tokens[0].replace("\"", "");
                    String cpuUsage = tokens[2].replace("\"", "");
                    String memoryUsage = tokens[4].replace("\"", "");

                    // Identify system or user processes based on process name
                    boolean isSystemProcess = processName.toLowerCase().contains("system") ||
                            processName.toLowerCase().contains("service");
                    boolean isUserProcess = !isSystemProcess;

                    // Print process details
                    System.out.println("Process Name: " + processName);
                    System.out.println("CPU Usage: " + cpuUsage);
                    System.out.println("Memory Usage: " + memoryUsage);
                    System.out.println("Is System Process: " + isSystemProcess);
                    System.out.println("Is User Process: " + isUserProcess);
                    System.out.println();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
