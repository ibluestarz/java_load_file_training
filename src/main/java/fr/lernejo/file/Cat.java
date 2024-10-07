package fr.lernejo.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Cat {
    public static int run(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("Missing argument");
            return 3;
        } else if (args.length > 1) {
            System.out.println("Too many arguments");
            return 4;
        } else {
            File file = new File(args[0]);
            if (file.isDirectory()) {
                System.out.println("A file is required");
                return 6;
            } else if (!file.exists()) {
                System.out.println("File not found");
                return 5;
            }
            if (file.length() > 3072) {
                System.out.println("File too large");
                return 7;
            }
            if (file.isFile()) {
                Scanner inputFile = new Scanner(file);
                // Read lines from the file until no more are left.
                while (inputFile.hasNext())
                {
                    // Read the next name.
                    String familyName = inputFile.nextLine();
                    // Display the last name read.
                    System.out.println(familyName);
                }
                // Close the file.
                inputFile.close();
            }
            return 0;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("Missing argument");
            exit(3);
        } else if (args.length > 1) {
            System.out.println("Too many arguments");
            exit(4);
        } else {
            File file = new File(args[0]);
            if (file.isDirectory()) {
                System.out.println("A file is required");
                exit(6);
            } else if (!file.exists()) {
                System.out.println("File not found");
                exit(5);
            }
            if (file.length() > 3072) {
                System.out.println("File too large");
                exit(7);
            }
            if (file.isFile()) {
                Scanner inputFile = new Scanner(file);
                // Read lines from the file until no more are left.
                while (inputFile.hasNext())
                {
                    // Read the next name.
                    String familyName = inputFile.nextLine();
                    // Display the last name read.
                    System.out.println(familyName);
                }
                // Close the file.
                inputFile.close();
            }
        }
    }
}
