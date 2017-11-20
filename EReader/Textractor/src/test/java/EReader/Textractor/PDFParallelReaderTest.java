package EReader.Textractor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

public class PDFParallelReaderTest {

    private final String PATH1 = "src/test/resources/pdf-sample.pdf";
    private final String PATH2 = "src/test/resources/long-pdf-sample.pdf";
    private File pdffile;
    private File longpdffile;
    private AbstractParallelReader reader;

    @Before
    public void setup(){
        pdffile = new File(PATH1);
        longpdffile = new File(PATH2);
        reader = new PDFParallelReader();
    }

    @After
    public void cleanup(){
        pdffile = null;
        longpdffile = null;
        reader = null;
    }

    @Test
    public void loadPDFParallelTest(){
        reader.loadFile(longpdffile);
        Scanner scanner = reader.readPage(11);
        while(scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }
}
