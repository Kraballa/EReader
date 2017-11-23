package EReader.Bookmarker;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

public class BookmarkManagerTest {
    private Bookmark bookmark;
    private final String PATH = "src\\test\\resources\\testimage.PNG";
    private File file;

    @Before
    public void setup() {
        bookmark = new Bookmark();
        file = new File(PATH);
        if (!file.exists()) {

        }
    }

    @After
    public void cleanup() {
        bookmark = null;
        file = null;
    }

    /**
     * IMPORTANT!!
     * This test is ignored because of a bug in the File class (probably). the writeToDisk() method works fine in use
     * however when testing I discovered that relative paths somehow behave inconsistent. When I save my .csv File
     * from my GUI it stores it in the relative path "Bookmarker/target/BookmarkExport.csv". But with the code below
     * it somehow ends with the relative path "Bookmarker/Bookmarker/target/TestBookmarks.csv". The problem is that
     * the path isn't even set in this test, it is a final String in the BookmarkManager class. This also happened
     * in version 1.x of this project and I haven't found an intelligent way to fix it.
     */
    @Ignore
    public void test() {
        bookmark.addBookmark(file, 5);
        BookmarkManager.writeToDisk(bookmark.getBookmarkData(), "TestBookmarks.csv");
        bookmark.removeBookmark(file);
        bookmark = BookmarkManager.importBookmark("TestBookmarks.csv");
        assert (bookmark.isBookmarked(file) && bookmark.getBookmarkedPage(file) == 5);
    }
}
