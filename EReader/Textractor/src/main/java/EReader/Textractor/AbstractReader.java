package EReader.Textractor;

import java.io.File;
import java.util.Scanner;

public abstract class AbstractReader implements BasicReader{
    private String[] content;
    private File file;

    /**
     * Loads an entire File. This incorporates opening the document, extracting the text and storing the data in a
     * Scanner[] for every page.
     * @param file  the file to load
     */
    public void loadFile(File file){
        this.file = file;
        content = new String[getPages()];

        for(int i = 1; i < content.length; i++){
            content[i-1] = loadPage(i);

        }
    }

    /**
     * Returns a Scanner that contains the text of a single page. This works indepentently of the data type since
     * it accesses the Scanner[] that stores all the data.
     * @param page  the page you want to display.
     * @return      the Scanner containing the data.
     */
    public Scanner readPage(Integer page) {
        if (content != null){
            if(content[page-1] != null) {
                return new Scanner(content[page - 1]);
            }else{
                if(file != null && file.exists()){
                    content[page-1] = loadPage(page);
                    return readPage(page);
                }else{
                    throw new IllegalStateException("Error, no File has been loaded yet.");
                }
            }
        }else{
            throw new IllegalStateException("Error, no File has been loaded yet.");
        }
    }

    /**
     * This opens, reads the text from, and closes one page of a document. Since this method is different for each
     * subclass, you have to implement it to have a functional program.
     * @param page
     * @return
     */
    public String loadPage(Integer page){
        throw new UnsupportedOperationException("Error, unimplemented method");
    }

    /**
     * Returns the number of pages in a file.
     * @return
     */
    public int getPages() {
        throw new UnsupportedOperationException("Error, unimplemented method");
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getExtension() {
        return null;
    }
}
