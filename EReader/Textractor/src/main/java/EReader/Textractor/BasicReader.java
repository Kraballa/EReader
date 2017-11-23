package EReader.Textractor;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public interface BasicReader {

    /**
     * Loads an entire File. This incorporates opening the document, extracting the text and storing the data in a
     * String[] for every page.
     * @param file  the file to load
     */
    void loadFile(File file) throws IOException;

    /**
     * After having loaded the pages with <code>loadFile()</code> you can get single Scanners that contain
     * the text on a specific page. This makes it so you don't have to scan the file repeatedly when switching
     * back and forth between pages like in the last version.
     * @param page  the page you want to display.
     * @return      a Scanner that contains the text of a page
     */
    public Scanner readPage(Integer page) throws IOException;

    /**
     * Returns the number of Pages.
     * @return  number of pages
     */
    public int getPages() throws IOException;

    /**
     * Loads a page from the specified file. In contrast to <code>readPage()</code> this accesses the file itself.
     * It is used on the initial load and is way slower than its counterpart.
     * @param page
     * @return
     */
    public String loadPage(Integer page) throws IOException;

    /**
     * Returns the extension the Reader handles.
     * @return      short String.
     */
    public String getExtension();
}
