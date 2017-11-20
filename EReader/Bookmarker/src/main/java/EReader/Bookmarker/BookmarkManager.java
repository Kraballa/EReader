package EReader.Bookmarker;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

/**
 * This class provides useful methods when working with Bookmarks and JTables. It let's you save bookmarks from
 * a TableModel to a file and load them later to a Bookmark, which again can be parsed to a TableModel.
 */
public class BookmarkManager {

    private static final String PATH = "Bookmarker/target/";


    /**
     * Creates a new BookmarkManager.
     */
    public BookmarkManager(){

    }

    /**
     * Writes a specific set of data to a specific file. The format is .csv which can be opened as a regular Text file,
     * or for more comfort in an Excel-like program.
     * @param aData the Data to save. This either used to be a TableModel or a Bookmark
     * @param filename
     */
    public static void writeToDisk(Object[][] aData, String filename) {

        System.out.println(PATH+filename);
        try {

            File file = new File(PATH + filename);
            System.out.println(PATH+filename);
            file.delete();

            System.out.println(file.getAbsolutePath());


            FileOutputStream fout = new FileOutputStream(file, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));
            //Headers
            bw.append("Files, Page");
            bw.append('\n');

            for (int row = 0; row < aData.length; row++) {
                for (int column = 0; column < aData[row].length; column++) {
                    if (aData[row][column] == null) {
                        bw.append("null");
                        // The comma separated value
                        bw.append(',');
                    } else {

                        bw.append(aData[row][column].toString());
                        bw.append(',');
                    }

                }

                bw.append('\n');
            }

            System.out.println(fout.toString());

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Imports the BookmarkExport.csv file and loads it into a Bookmark instance.
     * @return  an instance of Bookmark.
     */
    public static Bookmark importTable() {
        File file = new File(PATH + "BookmarkExport.csv");
        if (file.exists()) {
            Bookmark bookmark = new Bookmark();
            try {
                FileInputStream fin = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fin));

                String current = br.readLine();
                //to skip the first line that states the column names. They still appear when opening the file with excel.
                current = br.readLine();

                while (current != null) {
                    String[] splitInput = current.split(",");
                    bookmark.addBookmark(new File(splitInput[0]), Integer.parseInt(splitInput[1]));
                    current = br.readLine();
                }

                fin.close();
                br.close();

                return bookmark;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Bookmark();
    }

    /**
     * Loads the file and stores it in an ArrayList<File>.
     * @return
     */
    public ArrayList<File> getRecentFiles(){
        File file = new File("PDFReader.Bookmarker/target/BookmarkExport.csv");
        ArrayList<File> files = new ArrayList<>();
        if (file.exists()) {
            try {
                FileInputStream fin = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fin));

                String current = br.readLine();

                while (current != null) {
                    String[] splitInput = current.split(",");
                    File currentFile = new File(splitInput[0]);
                    files.add(currentFile);
                    current = br.readLine();
                }
                fin.close();
                br.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return files;
    }
}
