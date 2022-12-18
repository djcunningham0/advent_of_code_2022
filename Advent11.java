import java.io.IOException;
import java.util.*;

public class Advent11 {
    public static void main(String[] args) throws IOException {
        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_11.txt" : "data/11.txt";
        String[] data = Shared.readFile(file);

        // Part 1:
        MonkeyGroup monkeyGroup = new MonkeyGroup();
        for (int i = 0; i < data.length; i = i + 7) {
            String[] monkeyData = Arrays.copyOfRange(data, i+1, i+6);
            monkeyGroup.addMonkey(new Monkey(monkeyData));
        }

        monkeyGroup.processNRounds(20);

        // find the top two counts
        Integer[] counts = new Integer[monkeyGroup.monkeyList.size()];
        for (int i = 0; i < monkeyGroup.monkeyList.size(); i++) {
            counts[i] = monkeyGroup.monkeyList.get(i).inspectionCount;
        }
        Arrays.sort(counts, Collections.reverseOrder());

        long solution_a = (long) counts[0] * counts[1];
        System.out.println("Solution 11a: " + solution_a);

        if (example) {
           int correct_a = 10605;
           Shared.checkResult(solution_a, correct_a);
        }

        // Part 2:
        MonkeyGroup monkeyGroup2 = new MonkeyGroup();
        for (int i = 0; i < data.length; i = i + 7) {
            String[] monkeyData = Arrays.copyOfRange(data, i+1, i+6);
            monkeyGroup2.addMonkey(new Monkey(monkeyData));
        }

        monkeyGroup2.processNRounds(10000, false);

        Integer[] counts2 = new Integer[monkeyGroup2.monkeyList.size()];
        for (int i = 0; i < monkeyGroup2.monkeyList.size(); i++) {
            counts2[i] = monkeyGroup2.monkeyList.get(i).inspectionCount;
        }
        Arrays.sort(counts2, Collections.reverseOrder());

        long solution_b = (long) counts2[0] * counts2[1];
        System.out.println("Solution 11b: " + solution_b);

        if (example) {
           long correct_b = 2713310158L;
           Shared.checkResult(solution_b, correct_b);
        }
    }
}

class Monkey {
    int inspectionCount;
    Queue<Long> items;
    char operator;
    String changeVal;
    int testVal;
    int trueDest;
    int falseDest;

    public Monkey(String[] data) {
        inspectionCount = 0;

        // parse items
        Queue<Long> items = new LinkedList<>();
        String startingItems = data[0].split(": ")[1];  // after ": "
        for (String i : startingItems.split(", ")) {
            items.add(Long.parseLong(i));
        }
        this.items = items;

        // parse operator and changeVal
        String operation = data[1].split("= old ")[1];  // after "old "
        operator = operation.charAt(0);
        changeVal = operation.substring(2);

        // parse testVal
        testVal = Integer.parseInt(data[2].split("divisible by ")[1]);

        // parse trueDest and falseDest
        trueDest = Integer.parseInt(data[3].split("true: throw to monkey ")[1]);
        falseDest = Integer.parseInt(data[4].split("false: throw to monkey ")[1]);
    }

    protected long changeWorryLevel(long x, boolean divBy3) {
        // worry level increases according to operation, then gets divided by 3
        long y = (changeVal.equals("old")) ? x : Integer.parseInt(changeVal);
        long out = (operator == '+') ? x + y : x * y;
        out = (divBy3) ? out / 3 : out ;
        return out;
    }

    protected boolean checkLevel(long x) {
        return x % testVal == 0;
    }

    protected void addItem(long item) {
        items.add(item);
    }
}

class MonkeyGroup {
    ArrayList<Monkey> monkeyList;


    protected MonkeyGroup(ArrayList<Monkey> monkeyList) {
        this.monkeyList = monkeyList;
    }

    protected MonkeyGroup() {
        this.monkeyList = new ArrayList<>();
    }

    protected void addMonkey(Monkey monkey) {
        monkeyList.add(monkey);
    }

    protected void processRound() {
        processRound(true);
    }

    protected void processRound(boolean divBy3) {
        for (Monkey monkey : monkeyList) {
            while (!monkey.items.isEmpty()) {
                monkey.inspectionCount += 1;
                long item = monkey.items.remove();
                item = monkey.changeWorryLevel(item, divBy3);
                if (!divBy3) {
                    // If we're not dividing by 3, we need to do something else to keep
                    // the values under control. Take the modulo with the product of all
                    // the monkey test values. This retains all the relevant information
                    // and prevents values from growing too large.
                    item %= getTotalModulo();
                }
                int dest = (monkey.checkLevel(item)) ? monkey.trueDest : monkey.falseDest;
                monkeyList.get(dest).addItem(item);
            }
        }
    }

    protected void processNRounds(int n) {
        processNRounds(n, true);
    }

    protected void processNRounds(int n, boolean divBy3) {
        for (int i = 0; i < n; i++) {
            processRound(divBy3);
        }
    }

    protected int getTotalModulo() {
        int x = 1;
        for (Monkey monkey : monkeyList) {
            x *= monkey.testVal;
        }
        return x;
    }
}