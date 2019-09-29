import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvCreator {
    JFileChooser chooser = new JFileChooser();
    Scanner in = null;
    PrintWriter out = null;
    String input;

    private void chooseFile() {
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                in = new Scanner(selectedFile);
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e);
            }
        }
    }

    private void saveFile() {
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                out = new PrintWriter(selectedFile);
            } catch (FileNotFoundException e) {
                System.out.println("Save file not found: " + e);
            }
        }
    }

    public void processData() {
        int counter=0;
//        Open file dialog
        chooseFile();
//        Save file dialog
        saveFile();
//        data processing

//        Winkels Mario has three leading lines that we don't need, let's stip them
        for (int i=0;i<4;i++) {
            in.nextLine();
        }

//        Create column header
        out.println("location_name,address,housenumber,suffix,city,country,postalcode,tel.number,blank");
        while (in.hasNextLine()) {
            input = in.nextLine();
            input = input.trim(); // get rid of useless spaces
            input = input.toLowerCase(); // all in lowercase
            input = input.replaceAll(",", ""); // undo all data from delimiters
            input = input.replaceAll("/",""); // undo slashes
            counter++;
            switch (counter) {
//                    Name of location
//                case 1:
//                    break;
////                    address
//                case 2:
//                    break;
////                    housenumber and/or suffix
                case 3:
                    checkSuffix();
                    break;
////                    city
//                case 4:
//                    break;
////                    country iso
//                case 5:
//                    break;
//                    postalcode
                case 6:
                    input = input.replaceAll("\\s", "");
//                    System.out.println(input);
//                    checkSuffixAndPostalcode(false);
                    break;
//                    telephonenumber
                case 7:
//                    System.out.println("input before: " + input);
                    input = input.replaceAll("\\D", "");
//                    System.out.println("input after: " + input);
                    break;
//                    empty space inbetween records
                case 8:
                    out.println();
                    break;
            }
            if (counter != 8) {
                input += ",";
                out.print(input);
            } else {
                counter = 0;
            }
        }

//      Wrapping up
        out.close();
        in.close();
    }

    private void checkSuffix() {
//        Pattern pattern = Pattern.compile("(\\d*)(.+)(\\d*)");
        Pattern pattern = Pattern.compile("(\\d*)(.*)([a-zA-Z]*)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String a = matcher.group(1).trim();
            String b = matcher.group(2).trim();
            if (!a.isEmpty()) {
                input = a + "," + b;
//                System.out.println(input);
            }
        }
    }
}
