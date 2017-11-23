package EReader.Textractor;

import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PDFReaderTest {

    private final String PATH1 = "src/test/resources/pdf-sample.pdf";
    private final String PATH2 = "src/test/resources/encrypted-pdf-sample.pdf";
    private File pdffile;
    private File encryptedpdffile;
    private AbstractReader reader;

    @Before
    public void setup(){
        pdffile = new File(PATH1);
        encryptedpdffile = new File(PATH2);
        reader = new PDFReader();
    }

    @After
    public void cleanup(){
        pdffile = null;
        encryptedpdffile = null;
        reader = null;
    }

    @Test
    public void getPagesTest(){
        try {
            reader.loadFile(pdffile);
            assert (reader.getPages() == 1);
            reader.loadFile(encryptedpdffile);
            assert (reader.getPages() == 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getInvalidPagesTest(){

    }

    @Test
    public void loadPageTest(){
        try {
            reader.loadFile(pdffile);
            Scanner scanner = reader.readPage(1);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void loadEncryptedPageTest(){
        try {
            reader.loadFile(encryptedpdffile);
            Scanner scanner = reader.readPage(1);
            assert (scanner.nextLine().equals("PDF file is encrypted"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadEmptyPageTest(){
        try {
            reader.loadFile(pdffile);
            Scanner scanner = reader.readPage(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}