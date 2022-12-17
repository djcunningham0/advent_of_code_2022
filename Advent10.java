import java.io.IOException;

public class Advent10 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_10.txt" : "data/10.txt";
        String[] data = Shared.readFile(file);

        // Part 1:
        int x = 1;
        int cycle = 0;
        int totalVal = 0;
        for (String line : data) {
            if (line.startsWith("noop")) {
                cycle += 1;
                if (isImportantCycle(cycle)) {
                    totalVal += cycle * x;
                }
            } else {
                // addx command -- two cycles complete before x is updated
                for (int i = 0; i < 2; i++) {
                    cycle += 1;
                    if (isImportantCycle(cycle)) {
                        totalVal += cycle * x;
                    }
                }
                x += Integer.parseInt(line.substring(5));
            }
        }

        int solution_a = totalVal;
        System.out.println("Solution 10a: " + solution_a);

        if (example) {
           int correct_a = 13140;
           Shared.checkResult(solution_a, correct_a);
        }

        // Part 2:
        x = 1;
        cycle = 0;
        char[][] screen = new char[6][40];
        for (String line : data) {
            if (line.startsWith("noop")) {
                int row = cycle / 40;  // integer division
                int col = cycle % 40;
                screen[row][col] = draw(cycle, x);
                cycle += 1;
            } else {
                // addx command -- two cycles complete before x is updated
                for (int i = 0; i < 2; i++) {
                    int row = cycle / 40;  // integer division
                    int col = cycle % 40;
                    screen[row][col] = draw(cycle, x);
                    cycle += 1;
                }
                x += Integer.parseInt(line.substring(5));
            }
        }

        System.out.println("Solution 10b (read the letters):");
        for (char[] row : screen) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.print("\n");
        }
    }

    public static boolean isImportantCycle(int cycle) {
        return (cycle + 20) % 40 == 0;
    }

    public static char draw(int cycle, int x) {
        int i = cycle % 40;
        if (i >= x - 1 && i <= x + 1) {
            return '#';
        } else {
            return ' ';
        }
    }
}
