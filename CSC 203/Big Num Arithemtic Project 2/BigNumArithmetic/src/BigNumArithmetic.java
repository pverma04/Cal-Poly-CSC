import javax.swing.*;

public class BigNumArithmetic {

    /**
     * The entry point of the program.
     * @param args Command line arguments. Should have exactly one argument: a file name.
     */
    public static void main(String[] args) throws NumberException{
        /*
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Expected exactly 1 argument: a file name.");
        }
        String filePath = args[0];
        FileProcessor.processFile(filePath);
         */
        Number first = new Number("123456789.533456789");
        Number second = new Number("1423.39874200932");
        Number answer = first.subtract(second);
        System.out.println(answer.toString());
    }
}

