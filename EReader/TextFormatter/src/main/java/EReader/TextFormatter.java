package EReader;

import java.util.Scanner;

public class TextFormatter {

    /**
     * Recognizes when a new paragraph would start and deletes all obsolete newline commands.
     * @param scanner   the Scanner containing the String that needs to be formatted.
     * @return          new Scanner containing the formatted String.
     */
    public static Scanner autoformatParagraphs(Scanner scanner){
        String scanned = "";
        while(scanner.hasNextLine()){
            scanned += scanner.nextLine();
            scanned = scanned.trim().replaceAll("^ +| +$|( )+", "$1");
            if (scanned.lastIndexOf(".") > scanned.length()-3
                    || scanned.lastIndexOf("?") > scanned.length()-3
                    || scanned.lastIndexOf("!") > scanned.length()-3){
                scanned += "\n";
            }else{
                scanned += " ";
            }
        }
        return new Scanner(scanned);
    }

    /**
     * Replaces all \t with " ". This fixes a issue with some pdf-files where words are separated by tabs.
     * @param scanner   the Scanner containing the String that needs to be formatted.
     * @return          new Scanner containing the formatted String.
     */
    public static Scanner tabsToSpaces(Scanner scanner){
        String fixed = "";
        while (scanner.hasNextLine()) {
            fixed += scanner.nextLine().replaceAll("\t", " ");
            fixed += "\n";
        }
        return new Scanner(fixed);
    }

    /**
     * When formatting the paragraphs it can happen that words that were originally typed split (i.e. "gi- [NEWLINE]
     * raffe") appear in the same line (i.e. "gi- raffe"). This option tries to fix this by recognising when two words
     * match a certain pattern that most likely will only occurr in those situations. Because it's impossible to
     * distinguish cases where it's supposed to be like this, this stays an option.
     * @param scanner   the Scanner containing the String that needs to be formatted.
     * @return          new Scanner containing the formatted String.
     */
    public static Scanner joinSplitWords(Scanner scanner){
        String cleanText = joinScannerText(scanner);
        String[] split = cleanText.split("-[\\s]+");
        return new Scanner(concatenate(split));
    }

    /**
     * Removes a specified number of lines from the start of the Scanner.
     * @param scanner
     * @param head
     * @return
     */
    public static Scanner removeHeader(Scanner scanner, Integer head){
        String txt = "";
        for(int i = 0; i < head; i++){
            if(scanner.hasNextLine()){
                scanner.nextLine();
            }
        }
        while(scanner.hasNextLine()){
            txt += scanner.nextLine();
            if(scanner.hasNextLine()){
                txt += "\n";
            }
        }
        return new Scanner(txt);
    }

    /**
     * Removes a specified number of lines from the end of the Scanner.
     * @param scanner
     * @param foot
     * @return
     */
    public static Scanner removeFooter(Scanner scanner, Integer foot){
        String txt = "";
        while(scanner.hasNextLine()){
            txt += scanner.nextLine();
            txt += "\n";
        }
        if (foot > 0) {
            String[] split = txt.split("\n");
            txt = "";
            for(int i = 0; i < Math.max(split.length - foot,0); i++){
                txt += split[i];
                txt += "\n";
            }
        }
        return new Scanner(txt);
    }

    public static Scanner formatMultiple(Scanner scanner, Boolean join, Boolean paragraphs, Boolean tabs){
        Scanner newScanner = scanner;
        if(join){
            newScanner = joinSplitWords(newScanner);
        }
        if(paragraphs){
            newScanner = autoformatParagraphs(newScanner);
        }
        if(tabs){
            newScanner = tabsToSpaces(newScanner);
        }
        return newScanner;
    }

    public static Scanner formatAll(Scanner scanner){
        scanner = removeFooter(scanner,1);
        scanner = removeHeader(scanner, 1);
        return formatMultiple(scanner,true,true,true);
    }

    private static String joinScannerText(Scanner scanner){
        String cleanText = "";
        while(scanner.hasNextLine()){
            cleanText += scanner.nextLine();
            cleanText += "\n";
        }
        return cleanText;
    }

    private static String concatenate(String[] input){
        return concatenate(input,input.length);
    }

    private static String concatenate(String[] input, Integer ignore){
        String clean = "";
        for(int i = 0; i < Math.min(input.length,ignore); i++){
            clean += input[i];
        }
        return clean;
    }
}
