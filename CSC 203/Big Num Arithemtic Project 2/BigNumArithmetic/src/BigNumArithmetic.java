import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
public class BigNumArithmetic {

    /**
     * The entry point of the program.
     *
     * @param args Command line arguments. Should have exactly one argument: a file name.
     */
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException(
                        "Expected exactly 1 argument: a file name.");
            }
            String filePath = args[0];
            FileProcessor.processFile(filePath);

//            Number first = new Number("004");
//            Number second = new Number("005");
//            Number answer = first.multiply(second);
//            System.out.println(answer.toString());
//            String currentProblem = "13  * 3";
//            char operator = '*';


        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            //throw new RuntimeException(e);
        }
    }
}

