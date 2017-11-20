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

    public void addBookmark(File file, int page) {
        if(dataMap.containsKey(file)){
            dataMap.remove(file);
        }
        dataMap.put(file, page);
    }

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
        for(File current: dataMap.keySet()){
            files.add(current);
        }
        return files;
    }

    public Object[][] getBookmarkData() {

        int rowCount = dataMap.size();
        int columnCount = 2;

        Object[][] curTableData = new Object[rowCount][columnCount];

        int index = 0;
        for(File current: dataMap.keySet()){
            curTableData[index][0] = current;
            curTableData[index][1] = dataMap.get(current);
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
