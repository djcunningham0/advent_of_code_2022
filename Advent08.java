import java.io.IOException;
import java.util.Arrays;

public class Advent08 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_08.txt" : "data/08.txt";
        String[] data = Shared.readFile(file);

        // Part 1:
        int[][] grid = new int[data.length][data[0].length()];
        for (int i = 0; i < data.length; i++) {
            String[] row = data[i].split("");
            for (int j = 0; j < row.length; j++) {
                grid[i][j] = Integer.parseInt(row[j]);
            }
        }

        // start with everything marked as hidden, then go through and update if visible
        int[][] visibility = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // left visibility
                int[] leftArray = Arrays.copyOfRange(grid[i], 0, j);
                if (j == 0 || grid[i][j] > Arrays.stream(leftArray).max().getAsInt()) {
                    visibility[i][j] = 1;
                    continue;
                }

                // right visibility
                int [] rightArray = Arrays.copyOfRange(grid[i], j+1, grid[i].length);
                if (j == grid[i].length - 1 || grid[i][j] > Arrays.stream(rightArray).max().getAsInt()) {
                    visibility[i][j] = 1;
                    continue;
                }

                // top visibility
                int[] col = getColumn(grid, j);
                int [] topArray = Arrays.copyOfRange(col, 0, i);
                if (i == 0 || grid[i][j] > Arrays.stream(topArray).max().getAsInt()) {
                    visibility[i][j] = 1;
                    continue;
                }

                // bottom visibility
                int [] bottomArray = Arrays.copyOfRange(col, i+1, col.length);
                if (i == col.length - 1 || grid[i][j] > Arrays.stream(bottomArray).max().getAsInt()) {
                    visibility[i][j] = 1;
                }
            }
        }

        int solution_a = 0;
        for (int[] row : visibility) {
            for (int val : row) {
                solution_a += val;
            }
        }
        System.out.println("Solution 08a: " + solution_a);

        if (example) {
           int correct_a = 21;
           Shared.checkResult(solution_a, correct_a);
        }

        // Part 2:
        // don't need to check first/last rows/columns because they have 0 trees visible in one direction
        int maxScore = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[i].length - 1; j++) {
                int height = grid[i][j];

                // looking left
                int leftScore = 0;
                int[] leftArray = Arrays.copyOfRange(grid[i], 0, j);
                for (int k = leftArray.length - 1; k >= 0; k = k - 1) {
                    leftScore += 1;
                    if (leftArray[k] >= height) {
                        break;
                    }
                }

                // looking right
                int rightScore = 0;
                int[] rightArray = Arrays.copyOfRange(grid[i], j+1, grid[i].length);
                for (int value : rightArray) {
                    rightScore += 1;
                    if (value >= height) {
                        break;
                    }
                }

                // looking up
                int[] col = getColumn(grid, j);
                int topScore = 0;
                int [] topArray = Arrays.copyOfRange(col, 0, i);
                for (int k = topArray.length - 1; k >= 0; k = k - 1) {
                    topScore += 1;
                    if (topArray[k] >= height) {
                        break;
                    }
                }

                // looking down
                int bottomScore = 0;
                int [] bottomArray = Arrays.copyOfRange(col, i+1, col.length);
                for (int value : bottomArray) {
                    bottomScore += 1;
                    if (value >= height) {
                        break;
                    }
                }

                int score = leftScore * rightScore * topScore * bottomScore;
                if (score > maxScore) {
                    maxScore = score;
                }
            }
        }

        int solution_b = maxScore;
        System.out.println("Solution 08b: " + solution_b);

        if (example) {
           int correct_b = 8;
           Shared.checkResult(solution_b, correct_b);
        }
    }

    public static int[] getColumn(int[][] array, int index){
        int[] column = new int[array[0].length];
        for(int i=0; i<column.length; i++){
            column[i] = array[i][index];
        }
        return column;
    }
}
