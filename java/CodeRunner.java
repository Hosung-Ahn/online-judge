import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class CodeRunner {

    private static final String CPP_FILE_NAME = "./Main.cpp";
    private static final String INPUT_PATH = "./input.txt";
    private static final String RESULT_PATH = "./result.json";

    public static void main(String[] args) throws IOException, InterruptedException {
        String compileErrorInfo = null;
        String runtimeErrorInfo = null;
        String output = null;
        boolean compileError = false;
        boolean runtimeError = false;
        long executionTime = -1; // Initialize with -1 to indicate that the value is not set.

        // Compile the C++ program
        ProcessBuilder compileBuilder = new ProcessBuilder("g++", "-o", "Main", CPP_FILE_NAME);
        Process compileProcess = compileBuilder.start();
        boolean compileStatus = compileProcess.waitFor(10, TimeUnit.SECONDS);

        if (!compileStatus || compileProcess.exitValue() != 0) {
            compileError = true;
            compileErrorInfo = new String(compileProcess.getErrorStream().readAllBytes());
        }

        if (!compileError) {
            // Run the compiled C++ program with input
            ProcessBuilder runBuilder = new ProcessBuilder("./Main");
            runBuilder.redirectInput(Paths.get(INPUT_PATH).toFile());
            runBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
            runBuilder.redirectError(ProcessBuilder.Redirect.PIPE);

            long startTime = System.currentTimeMillis();
            Process runProcess = runBuilder.start();
            boolean executionStatus = runProcess.waitFor(10, TimeUnit.SECONDS);
            long endTime = System.currentTimeMillis();

            if (!executionStatus || runProcess.exitValue() != 0) {
                runtimeError = true;
                runtimeErrorInfo = new String(runProcess.getErrorStream().readAllBytes());
            } else {
                output = new String(runProcess.getInputStream().readAllBytes());
                executionTime = endTime - startTime; // Time in milliseconds
            }
        }

        // Create and write result to JSON
        writeResultToJson(compileError, compileErrorInfo, runtimeError, runtimeErrorInfo, executionTime, output);
    }

    private static void writeResultToJson(boolean compileError, String compileInfo, boolean runtimeError,
                                          String runtimeInfo, long executionTime, String output) throws IOException {
        String result = String.format(
                "{\n" +
                        "  \"compile_error\": %b,\n" +
                        "  \"compile_info\": \"%s\",\n" +
                        "  \"runtime_error\": %b,\n" +
                        "  \"runtime_info\": \"%s\",\n" +
                        "  \"execution_time\": %d,\n" +
                        "  \"output\": \"%s\"\n" +
                        "}", compileError, escapeJson(compileInfo), runtimeError, escapeJson(runtimeInfo), executionTime, escapeJson(output)
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_PATH))) {
            writer.write(result);
        }
    }

    // Escape special characters for JSON string
    private static String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
