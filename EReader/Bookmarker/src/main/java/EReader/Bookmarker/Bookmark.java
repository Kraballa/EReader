package EReader.Bookmarker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Bookmark {

    private HashMap<File, Integer> dataMap;

    public Bookmark(){
        dataMap = new HashMap<>();
    }

    public Bookmark(HashMap<File,Integer> hashMap){
        dataMap = hashMap;
    }

    /**
     * Adds a Bookmark. A Bookmark represents a function where one file corresponds to an integer. It's designed to
     * be used in a reader-like environment where you want to save a page to come back to later like a book.
     * @param file  file to bookmark.
     * @param page  page to bookmark.
     */
    public void addBookmark(File file, int page) {
        if(dataMap.containsKey(file)){
            dataMap.remove(file);
        }
        dataMap.put(file, page);
    }

    /**
     * Removes a Bookmark.
     * @param file  the file to remove the bookmark for.
     */
    public void removeBookmark(File file) {
        if(dataMap.containsKey(file)){
            dataMap.remove(file);
        }
    }

    public void clear() {
        dataMap.clear();
    }

    public boolean isBookmarked(File file) {
        return dataMap.containsKey(file);
    }

    public HashMap<File, Integer> getBookmarks() {
        return dataMap;
    }

    public int getTotalBookmarks() {
        return dataMap.size();
    }

    public ArrayList<File> getFiles(){
        ArrayList<File> files = new ArrayList<>();
        files.addAll(dataMap.keySet());
        return files;
    }

    /**
     * Converts the data of all stored bookmarks to a 2d array of Objects. An Object can be stored much easier than
     * a complex structure like a bookmark. Use the BookmarkManager class to do that.
     * @return  2d array of Objects
     */
    public Object[][] getBookmarkData() {

        int rowCount = dataMap.size();
        int columnCount = 2;

        Object[][] curTableData = new Object[rowCount][columnCount];

        int index = 0;
        for(File current: dataMap.keySet()){
            curTableData[index][0] = current;
            curTableData[index][1] = getBookmarkedPage(current);
            index++;
        }
        return curTableData;
    }

    public int getBookmarkedPage(File file){
        if(dataMap.containsKey(file)){
            return dataMap.get(file);
        }else{
            return -1;
        }
    }
}
