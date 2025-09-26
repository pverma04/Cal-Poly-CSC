import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileProcessor {

    /**
     * Processes arithmetic expressions line-by-line in the given file.
     *
     * @param filePath Path to a file containing arithmetic expressions.
     */
    public static void processFile(String filePath) {
        File infile = new File(filePath);
        try (Scanner scan = new Scanner(infile)) {
            FileWriter output = new FileWriter("output.txt");
            Number firstNum = null;
            Number secondNum = null;
            Number answer = null;
            char operator = ' ';
            int posOperator;
            String currentProblem;
            boolean firstPrint = true;
            while (scan.hasNextLine()) {
                // TODO: Process each line of the input file here.
                currentProblem = scan.nextLine().replaceAll("\\s", ""); //problem with NO spaces
                if(!currentProblem.equals("")){
                    if(currentProblem.indexOf('+') > 0) {
                        operator = '+';
                        posOperator = currentProblem.indexOf('+');
                        firstNum = new Number(currentProblem.substring(0, posOperator).replaceFirst("^0+(?!$)", ""));
                        secondNum = new Number(currentProblem.substring(posOperator + 1).replaceFirst("^0+(?!$)", ""));
                        answer = firstNum.add(secondNum); //WRITE TO FILE
                    }
                    if (currentProblem.indexOf('*') > 0){
                        operator = '*';
                        posOperator = currentProblem.indexOf('*');
                        firstNum = new Number(currentProblem.substring(0, posOperator).replaceFirst("^0+(?!$)", ""));
                        secondNum = new Number(currentProblem.substring(posOperator + 1).replaceFirst("^0+(?!$)", ""));
                        answer = firstNum.multiply(secondNum); //WRITE TO FILE
                    }
                    if (currentProblem.indexOf('^') > 0) {
                        operator = '^';
                        posOperator = currentProblem.indexOf('^');
                        firstNum = new Number(currentProblem.substring(0, posOperator).replaceFirst("^0+(?!$)", ""));
                        secondNum = new Number(currentProblem.substring(posOperator + 1).replaceFirst("^0+(?!$)", ""));
                        answer = firstNum.exponent(secondNum); //WRITE TO FILE
                    }
                    if(!firstPrint){
                        System.out.print("\n");
                    }
                    firstPrint = false;
                    System.out.print(firstNum.toString() + " " + operator + " " + secondNum.toString() + " = " + answer.toString());
                    output.write(firstNum.toString() + " " + operator + " " + secondNum.toString() + " = " + answer.toString() + "\n");
                }
            }
            output.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException | NumberException e) {
                throw new RuntimeException(e);
        }
    }
}
