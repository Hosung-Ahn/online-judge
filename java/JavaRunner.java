import java.io.*;
import java.util.concurrent.TimeUnit;

public class JavaRunner {
    private static final String MAIN_PATH = "Main.java";
    private static final String INPUT_PATH = "input.txt";

    public static void main(String[] args) {
        int timeLimitInMs = Integer.parseInt(args[0]);

        try {
            Process compileProcess = new ProcessBuilder("javac", MAIN_PATH).start();
            boolean compileFinished = compileProcess.waitFor(timeLimitInMs, TimeUnit.MILLISECONDS);

            if (!compileFinished || compileProcess.exitValue() != 0) {
                createResult(false, "Compile Error", timeLimitInMs, "");
                return;
            }

            ProcessBuilder processBuilder = new ProcessBuilder("java", "Main");
            processBuilder.redirectInput(new File(INPUT_PATH));
            Process process = processBuilder.start();

            long startTime = System.nanoTime();
            boolean finished = process.waitFor(timeLimitInMs, TimeUnit.MILLISECONDS);
            long endTime = System.nanoTime();
            long executionTimeInMs = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

            if (!finished) {
                createResult(false, "Time Limit Exceeded", timeLimitInMs, "");
                process.destroy();
                return;
            }

            String output = readStream(process.getInputStream());

            if (process.exitValue() != 0) {
                createResult(false, "Runtime Error", (int) executionTimeInMs, output);
                return;
            } else {
                createResult(true, "", (int) executionTimeInMs, output);
            }
        } catch (IOException | InterruptedException e) {
            createResult(false, "Server Error", timeLimitInMs, "");
        }

    }

    private static void createResult(boolean success, String error, int executionTimeInMs, String output) {
        System.out.println(success);
        System.out.println(error);
        System.out.println(executionTimeInMs);
        System.out.println(output);
    }

    private static String readStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            createResult(false, "Server Error", 0, "");
        }
        return builder.toString();
    }
}
