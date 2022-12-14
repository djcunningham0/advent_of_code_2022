import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Shared {
    static String[] readFile(String fileName) throws IOException {
        // list that holds strings of a file
        List<String> listOfStrings
                = new ArrayList<String>();

        // load data from file
        BufferedReader bf = new BufferedReader(
                new FileReader(fileName));

        // read entire line as string
        String line = bf.readLine();

        // checking for end of file
        while (line != null) {
            listOfStrings.add(line);
            line = bf.readLine();
        }

        // closing bufferreader object
        bf.close();

        // storing the data in arraylist to array
        return listOfStrings.toArray(new String[0]);
    }

    static void checkResult(int checkVal, int correctVal) {
        if (checkVal == correctVal) {
            System.out.println("Correct!");
        } else {
            System.out.println("***** Incorrect solution. Should be " + correctVal + " *****");
        }
    }

    static void checkResult(long checkVal, long correctVal) {
        if (checkVal == correctVal) {
            System.out.println("Correct!");
        } else {
            System.out.println("***** Incorrect solution. Should be " + correctVal + " *****");
        }
    }

    static void checkResult(String checkVal, String correctVal) {
        if (checkVal.equals(correctVal)) {
            System.out.println("Correct!");
        } else {
            System.out.println("***** Incorrect solution. Should be " + correctVal + " *****");
        }
    }
}
