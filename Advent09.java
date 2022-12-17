import java.io.IOException;
import java.util.HashSet;

public class Advent09 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));
        boolean example2 = ((args.length > 0) && (args[0].equals("example2")));

        String file;
        if (example) {
            file = "data/example_09.txt";
        } else if (example2) {
            file = "data/example_09_2.txt";
        } else {
            file = "data/09.txt";
        }
        String[] data = Shared.readFile(file);

        // Part 1:
        int head_x = 0;
        int head_y = 0;
        int tail_x = 0;
        int tail_y = 0;
        HashSet<String> tailLocs = new HashSet<>();
        tailLocs.add(tail_x + "," + tail_y);
        for (String row : data) {
            char dir = row.charAt(0);
            int n = Integer.parseInt(row.substring(2));
            for (int i = 0; i < n; i++) {
                // make one move at a time in the appropriate direction
                if (dir == 'R') {
                    head_x += 1;
                } else if (dir == 'L') {
                    head_x -= 1;
                } else if (dir == 'U') {
                    head_y += 1;
                } else {
                    head_y -= 1;
                }

                // move the tail to keep up, then mark its location
                if (head_x - tail_x > 1) {
                    tail_x = head_x - 1;
                    tail_y = head_y;
                } else if (head_x - tail_x < -1) {
                    tail_x = head_x + 1;
                    tail_y = head_y;
                } else if (head_y - tail_y > 1) {
                    tail_y = head_y - 1;
                    tail_x = head_x;
                } else if (head_y - tail_y < -1) {
                    tail_y = head_y + 1;
                    tail_x = head_x;
                }
                tailLocs.add(tail_x + "," + tail_y);
            }
        }

        int solution_a = tailLocs.size();
        System.out.println("Solution 09a: " + solution_a);

        if (example) {
           int correct_a = 13;
           Shared.checkResult(solution_a, correct_a);
        }

        // Part 2:
        int nKnots = 10;
        int[] x = new int[nKnots];
        int[] y = new int[nKnots];
        HashSet<String> tailLocs2 = new HashSet<>();
        tailLocs2.add(x[nKnots-1] + "," + y[nKnots-1]);
        for (String row : data) {
            char dir = row.charAt(0);
            int n = Integer.parseInt(row.substring(2));
            for (int z = 0; z < n; z++) {
                // make one move with head at a time in the appropriate direction
                if (dir == 'R') {
                    x[0] += 1;
                } else if (dir == 'L') {
                    x[0] -= 1;
                } else if (dir == 'U') {
                    y[0] += 1;
                } else {
                    y[0] -= 1;
                }

                // update all of the other locations
                for (int i = 1; i < nKnots; i++) {
                    int x_diff = x[i-1] - x[i];
                    int y_diff = y[i-1] - y[i];
                    // note: now there's the possibility of leading nodes moving diagonally
                    if (x_diff > 1 && y_diff > 1) {
                        x[i] = x[i-1] - 1;
                        y[i] = y[i-1] - 1;
                    } else if (x_diff > 1 && y_diff < -1) {
                        x[i] = x[i-1] - 1;
                        y[i] = y[i-1] + 1;
                    } else if (x_diff < -1 && y_diff > 1) {
                        x[i] = x[i-1] + 1;
                        y[i] = y[i-1] - 1;
                    } else if (x_diff < -1 && y_diff < -1) {
                        x[i] = x[i-1] + 1;
                        y[i] = y[i-1] + 1;
                    } else if (x_diff > 1) {
                        x[i] = x[i-1] - 1;
                        y[i] = y[i-1];
                    } else if (x_diff < -1) {
                        x[i] = x[i-1] + 1;
                        y[i] = y[i-1];
                    } else if (y_diff > 1) {
                        y[i] = y[i-1] - 1;
                        x[i] = x[i-1];
                    } else if (y_diff < -1) {
                        y[i] = y[i-1] + 1;
                        x[i] = x[i-1];
                    }
                }

//                if (!tailLocs2.contains(x[nKnots - 1] + "," + y[nKnots - 1])) {
//                    System.out.println(x[nKnots-1] + "," + y[nKnots-1]);
//                }

                // mark the tail location
                tailLocs2.add(x[nKnots-1] + "," + y[nKnots-1]);
            }
        }

        int solution_b = tailLocs2.size();
        System.out.println("Solution 09b: " + solution_b);

        if (example) {
           int correct_b = 1;
           Shared.checkResult(solution_b, correct_b);
        } else if (example2) {
            int correct_b = 36;
            Shared.checkResult(solution_b, correct_b);
        }
    }
}
