package EReader.Textractor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PDFReader extends AbstractParallelReader{

    @Override
    public String loadPage(Integer page) {
        try{
            PDDocument doc = PDDocument.load(getFile());
            String txt;
            if(!doc.isEncrypted()){
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setStartPage(page);
                stripper.setEndPage(page);
                txt = stripper.getText(doc);
            }else{
                txt = "PDF file is encrypted";
            }
            doc.close();
            return txt;
        }catch(IOException e){
            e.printStackTrace();
            return "Error reading page " + page;
        }
    }

    @Override
    public int getPages() {
        try{
            PDDocument doc = PDDocument.load(getFile());
            int pages = doc.getNumberOfPages();
            doc.close();
            return pages;
        }catch(IOException e){
            return 0;
        }
    }

    @Override
    public String getExtension() {
        return "pdf";
    }
}


