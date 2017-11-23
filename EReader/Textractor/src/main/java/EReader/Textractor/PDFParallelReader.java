package EReader.Textractor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;

public class PDFParallelReader extends AbstractParallelReader {

    @Override
    public String loadPage(Integer page) throws IOException {
        try {
            PDDocument doc = PDDocument.load(getFile());
            String txt;
            if (!doc.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setStartPage(page);
                stripper.setEndPage(page);
                txt = stripper.getText(doc);
            } else {
                txt = "PDF file is encrypted";
            }
            doc.close();
            return txt;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading page " + page;
        }
    }

    @Override
    public int getPages() throws IOException {
        PDDocument doc = PDDocument.load(getFile());
        int pages = doc.getNumberOfPages();
        if (pages == 0) {
            throw new IOException("Error, invalid PDF file");
        }
        doc.close();
        return pages;
    }

    @Override
    public String getExtension() {
        return "pdf";
    }
}


