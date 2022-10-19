import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BigNumArithmetic {

    /**
     * The entry point of the program.
     * @param args Command line arguments. Should have exactly one argument: a file name.
     */
    public static void main(String[] args) throws NumberException{
        try {
            Scanner input = new Scanner(new File());
            FileWriter output = new FileWriter("Output.txt");

            String currentProblem;
            Number firstNum;
            Number secondNum;
            Number answer = new Number();
            char operator = ' ';
            List<Character> operators = new ArrayList<>();
            operators.add('+');
            operators.add('*');
            operators.add('^');
            while (input.hasNextLine()) {
                currentProblem = input.nextLine().replaceAll("\\s", ""); //problem with NO spaces
                int operatorIndex = -1;
                for(int i = 0; i < currentProblem.length(); i++) {
                    switch (currentProblem.charAt(i)) {
                        case '+':
                            operatorIndex = i;
                            operator = '+';
                            break;
                        case '*':
                            operatorIndex = i;
                            operator = '*';
                            break;
                        case '^':
                            operatorIndex = i;
                            operator = '^';
                            break;
                    }
                }
                if (operatorIndex == -1) { //none of the valid operators are present
                    System.out.println("Invalid problem");
                    return;
                } else { //valid problem
                    firstNum = new Number(currentProblem.substring(0, operatorIndex).replaceFirst("^0+(?!$)", ""));
                    secondNum = new Number(currentProblem.substring(operatorIndex + 1).replaceFirst("^0+(?!$)", ""));
                    switch (operator) {
                        case '+':
                            answer = firstNum.add(secondNum); //WRITE TO FILE
                            output.write(firstNum.toString() + " + " + secondNum.toString() + " = " + answer.toString());
                        case '*':
                            answer = firstNum.multiply(secondNum); //WRITE TO FILE
                            output.write(firstNum.toString() + " * " + secondNum.toString() + " = " + answer.toString());
                        case '^':
                            answer = firstNum.exponent(secondNum); //WRITE TO FILE
                            output.write(firstNum.toString() + " * " + secondNum.toString() + " = " + answer.toString());

                    }
                }
            }
            input.close();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Expected exactly 1 argument: a file name.");
        }
        String filePath = args[0];
        FileProcessor.processFile(filePath);

        String currentProblem = "1876087 9    + 10";
        System.out.println(currentProblem);
        currentProblem = currentProblem.replaceAll("\\s", "");
        System.out.println(currentProblem);
         */


//        Number first = new Number("004");
//        Number second = new Number("005");
//        Number answer = first.multiply(second);
//        System.out.println(answer.toString());


    }
}

