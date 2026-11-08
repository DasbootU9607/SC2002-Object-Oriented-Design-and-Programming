package restaurantrush;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Main {
    private Main() {}
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--help")) {
            System.out.println("Usage: java restaurantrush.Main [--replay FILE]");
            return;
        }
        boolean replay = args.length == 2 && args[0].equals("--replay");
        if (args.length != 0 && !replay) {
            System.err.println("Usage: java restaurantrush.Main [--replay FILE]");
            System.exit(2);
            return;
        }
        try (Reader input = replay ? Files.newBufferedReader(Path.of(args[1]), StandardCharsets.UTF_8)
                : new InputStreamReader(System.in, StandardCharsets.UTF_8)) {
            new ConsoleUI(input, new PrintWriter(System.out, true), replay).play(new GameEngine(new GameSetup()));
        } catch (IOException ex) {
            System.err.println("Game stopped: " + ex.getMessage());
            System.exit(2);
        }
    }
}
