package EReader.Bookmarker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class BookmarkTest {
    private final String PATH = "src\\test\\resources\\testimage.PNG";
    private File file;
    private Bookmark bookmark;

    @Before
    public void setup() {
        file = new File(PATH);
        bookmark = new Bookmark();
    }

    @After
    public void cleanup() {
        file = null;
        bookmark = null;
    }

    @Test
    public void bookmarkingTest() {
        bookmark.addBookmark(file, 5);
        assert (bookmark.isBookmarked(file) && bookmark.getBookmarkedPage(file) == 5);
    }

    @Test
    public void getBookmarksTest() {
        bookmark.addBookmark(file, 5);
        assert (bookmark.getFiles().contains(file));
        assert (bookmark.getBookmarks().containsKey(file));
        assert (bookmark.getBookmarks().get(file) == 5);
    }

    @Test
    public void duplicateBookmarkTest() {
        bookmark.addBookmark(file, 1);
        bookmark.addBookmark(file, 2);
        assert (bookmark.getBookmarkedPage(file) == 2);
    }

    @Test
    public void isBookmarkedTest() {
        bookmark.addBookmark(file, 1);
        assert (bookmark.isBookmarked(file) && bookmark.getFiles().contains(file));
        bookmark.removeBookmark(file);
        assert (!bookmark.isBookmarked(file) && !bookmark.getFiles().contains(file));
    }

    @Test
    public void totalBookmarksTest() {
        bookmark.addBookmark(file, 6);
        assert (bookmark.getTotalBookmarks() == 1);
    }

    @Test
    public void bookmarkDataTest() {
        bookmark.addBookmark(file, 5);
        Object[][] data = bookmark.getBookmarkData();
        assert (data[0][0].equals(file) && data[0][1].equals(5));
    }
}
