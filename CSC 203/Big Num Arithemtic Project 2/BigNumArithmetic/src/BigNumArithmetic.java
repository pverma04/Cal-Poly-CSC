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
    public static void main(String[] args) throws NumberException {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException(
                        "Expected exactly 1 argument: a file name.");
            }
            String filePath = args[0];
            FileProcessor.processFile(filePath);


        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
//        Number first = new Number("102");
//        Number second = new Number("1000");
//        System.out.println(first.multiply(second).toString());
    }
}

