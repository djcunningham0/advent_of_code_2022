import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Advent12 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_12.txt" : "data/12.txt";
        String[] data = Shared.readFile(file);

        // Part 1:
        int[][] grid = new int[data.length][data[0].length()];
        int xStart = 0;
        int yStart = 0;
        int xEnd = 0;
        int yEnd = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length(); j++) {
                int val = data[i].charAt(j);
                if (val == 'S') {
                    grid[i][j] = 'a';
                    xStart = i;
                    yStart = j;
                } else if (val == 'E') {
                    grid[i][j] = 'z';
                    xEnd = i;
                    yEnd = j;
                } else {
                    grid[i][j] = data[i].charAt(j);
                }
            }
        }

        int solution_a = dijkstra(grid, xStart, yStart, xEnd, yEnd);
        System.out.println("Solution 12a: " + solution_a);

        if (example) {
           int correct_a = 31;
           Shared.checkResult(solution_a, correct_a);
        }

        // Part 2:
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length(); j++) {
                if (data[i].charAt(j) == 'a') {
                    int distance = dijkstra(grid, i, j, xEnd, yEnd);
                    if (distance < minDistance) {
                        minDistance = distance;
                    }
                }
            }
        }

        int solution_b = minDistance;
        System.out.println("Solution 12b: " + solution_b);

        if (example) {
           int correct_b = 29;
           Shared.checkResult(solution_b, correct_b);
        }
    }

    private static int dijkstra(int[][] grid, int xStart, int yStart, int xEnd, int yEnd) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        HashSet<Node> visited = new HashSet<>();
        HashMap<Node, Integer> distances = new HashMap<>();

        // add starting node to queue with cost 0
        Node startNode = new Node(xStart, yStart, 0);
        queue.add(startNode);
        distances.put(startNode, 0);

        int i = 0;
        while (!queue.isEmpty() && i < 10000000) {
            i += 1;
            // take the first point in the queue, which will be the one with the shortest distance to it
            Node currentNode = queue.remove();
            if (visited.contains(currentNode)) {
                continue;
            }
            visited.add(currentNode);
            int distance = distances.getOrDefault(currentNode, Integer.MAX_VALUE);

            // if we've reached the destination, we're done
            if (currentNode.x == xEnd && currentNode.y == yEnd) {
                break;  // distance is the solution
            }

            // # get the distance to each neighbor
            // -- if it's less than we've seen previously, add it to the queue
            for (Node nextNode : getNeighbors(currentNode, grid)) {
                if (visited.contains(nextNode)) {
                    continue;
                }
                int newDistance = distance + 1;
                if (newDistance < distances.getOrDefault(nextNode, Integer.MAX_VALUE)) {
                    distances.put(nextNode, newDistance);
                    nextNode.cost = newDistance;
                    queue.add(nextNode);
                }
            }
        }
        return distances.getOrDefault(new Node(xEnd, yEnd), Integer.MAX_VALUE);
    }

    private static List<Node> getNeighbors(Node node, int[][] grid) {
        return getNeighbors(node, grid, 1);
    }

    private static List<Node> getNeighbors(Node node, int[][] grid, int maxStep) {
        int xMax = grid.length;
        int yMax = grid[0].length;

        List<Node> neighbors = new ArrayList<>();
        if (node.x < xMax - 1) {
            int xNext = node.x + 1;
            int yNext = node.y;
            if (grid[xNext][yNext] - grid[node.x][node.y] <= maxStep) {
                neighbors.add(new Node(xNext, yNext));
            }
        }
        if (node.y < yMax - 1) {
            int xNext = node.x;
            int yNext = node.y + 1;
            if (grid[xNext][yNext] - grid[node.x][node.y] <= maxStep) {
                neighbors.add(new Node(xNext, yNext));
            }
        }
        if (node.x > 0) {
            int xNext = node.x - 1;
            int yNext = node.y;
            if (grid[xNext][yNext] - grid[node.x][node.y] <= maxStep) {
                neighbors.add(new Node(xNext, yNext));
            }
        }
        if (node.y > 0) {
            int xNext = node.x;
            int yNext = node.y - 1;
            if (grid[xNext][yNext] - grid[node.x][node.y] <= maxStep) {
                neighbors.add(new Node(xNext, yNext));
            }
        }
        return neighbors;
    }
}

class Node implements Comparable<Node> {
    final int x;
    final int y;
    int cost;
    private final int hashCode;

    protected Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.cost = 0;
        this.hashCode = Objects.hash(x, y);
    }

    protected Node(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.hashCode = Objects.hash(x, y);
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.cost, o.cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Node that = (Node) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
