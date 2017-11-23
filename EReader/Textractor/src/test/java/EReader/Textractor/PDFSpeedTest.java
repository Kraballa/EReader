package EReader.Textractor;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PDFSpeedTest {

    private PDFReader reader;
    private PDFParallelReader parallelReader;
    private final String PATH = "src/test/resources/encrypted-pdf-sample.pdf";
    private File longpdffile;

    @Before
    public void setup(){
        longpdffile = new File(PATH);
        reader = new PDFReader();
        parallelReader = new PDFParallelReader();
    }

    @After
    public void cleanup(){
        longpdffile = null;
        reader = null;
    }

    @Test
    public void timeLoadTest(){
        double parallelavg;
        double sequenceavg;
        double acceleration;
        double efficiency;
        int pages = 4;
        int cores = Runtime.getRuntime().availableProcessors();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            try {
                reader.loadFile(longpdffile);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            System.out.println((i+1)+": Sequencially loaded "+(pages+i*pages)+" pages in "+(System.currentTimeMillis() - startTime)+" ms.");
        }
        long endTime = System.currentTimeMillis();
        sequenceavg = (endTime-startTime)/5;
        System.out.println("Avg: " + sequenceavg + " cores: 1");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            try {
                parallelReader.loadFile(longpdffile);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            System.out.println((i+1)+": parallel loaded "+(pages +i*pages)+" pages in "+(System.currentTimeMillis() - startTime)+" ms.");
        }
        endTime = System.currentTimeMillis();
        parallelavg = (endTime-startTime)/5;
        System.out.println("Avg: " + parallelavg + " cores: " + cores);

        acceleration = sequenceavg/parallelavg;
        efficiency = acceleration/cores;

        System.out.println("-------------------------------------");
        System.out.println("Acceleration: " + acceleration + "\nEfficiency: " + efficiency);

    }
}
