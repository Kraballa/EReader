import EReader.TextFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class TextFormatterTest {

    public final String testString = "This text shou- ld be in the fi-\nrst line and without '-' symbols.\n" +
            "This is the se- cond line and th-\n is too. If\tthis\tsentence\tis\tweirdly\tformatted, tabstospaces isn't working.\n" +
            "this next part is the 3rd\n" +
            "line. If this is not the 3rd line\n" +
            "autoParagraphing isn't working.\n" +
            "If this is the 4th.\n" +
            "and 5th line, it should work fine.";

    public final String wrongTabString = "This\tString\thas\ttabbing\tissues.\nThe rest should be ok. Hopefully it works!";
    public final String wrongJoinString = "Th- is String has spl-\nit wo-\n rd iss-  ues.";
    public final String wrongParagraphString = "This\nString\nhas\nParagraph issues.\n2nd Line here. Still 2nd Line.";
    public final String removeHeaderString = "This line shouldn't appear.\nThis line should appear";
    public final String removeFootString = "1\n2\n3\n4\n5";

    @Before
    public void setup(){

    }

    @After
    public void cleanup(){

    }

    @Test
    public void joinWordsTest(){
        Scanner scanner = TextFormatter.joinSplitWords(new Scanner(testString));
        printResult(scanner);
    }

    @Test
    public void tabsToSpacesTest(){
        Scanner scanner = TextFormatter.tabsToSpaces(new Scanner(testString));
        printResult(scanner);
    }

    @Test
    public void autoformatParagraphsTest(){
        Scanner scanner = TextFormatter.autoformatParagraphs(new Scanner(testString));
        printResult(scanner);
    }

    @Test
    public void testJoinParagraphTabs(){
        Scanner scanner = TextFormatter.formatAll(new Scanner(testString));
        printResult(scanner);
        scanner = TextFormatter.formatAll(new Scanner(wrongJoinString));
        printResult(scanner);
        scanner = TextFormatter.formatAll(new Scanner(wrongParagraphString));
        printResult(scanner);
        scanner = TextFormatter.formatAll(new Scanner(wrongTabString));
        printResult(scanner);
    }

    @Test
    public void removeHeaderTest(){
        Scanner scanner = TextFormatter.removeHeader(new Scanner(removeFootString),1);
        printResult(scanner);
    }

    @Test
    public void removeFootTest(){
        Scanner scanner = TextFormatter.removeFooter(new Scanner(removeFootString),20);
        printResult(scanner);
    }

    private void printResult(Scanner scanner){
        while(scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }
        System.out.println("\n");
    }
}
