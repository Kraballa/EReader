package EReader.MainWindow;

import EReader.Bookmarker.Bookmark;
import EReader.Bookmarker.BookmarkManager;
import EReader.TextFormatter;
import EReader.Textractor.AbstractParallelReader;
import EReader.Textractor.PDFParallelReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class WindowController {

    private File file;
    private AbstractParallelReader reader;
    private int page = 1;
    private Bookmark bookmark;

    @FXML
    private TextArea mainTextArea;
    @FXML
    private Label loadedLabel;
    @FXML
    private TextField changePage;
    @FXML
    private Label maxPageLabel;
    @FXML
    private CheckBox tabFix;
    @FXML
    private CheckBox splitWordFix;
    @FXML
    private CheckBox paragraphFix;
    @FXML
    private Menu bookmarkSubmenu;

    @FXML
    public void initialize() {
        manageOpenBookmarkedSubmenu();
    }

    public void close() {
        exitApplication();
    }

    public void openFile() {
        openFile(null);
    }

    public void openFile(File fileSelected) {
        if (fileSelected != null && fileSelected.exists()) {
            file = fileSelected;
        } else {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open PDF File");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            file = fc.showOpenDialog(null);
        }

        long startTime = System.currentTimeMillis();

        if (file != null && file.exists()) {
            loadedLabel.setText("loading...");
            reader = new PDFParallelReader();
            int pages = 0;
            try {
                reader.loadFile(file);
                pages = reader.getPages();
            } catch (IOException e) {
                openErrorWindow();
                e.printStackTrace();
                loadedLabel.setText("Error loading " + file.getName());
                return;
            }

            bookmark = BookmarkManager.importBookmark("BookmarkExport.csv");
            if (bookmark.isBookmarked(file)) {
                page = bookmark.getBookmarkedPage(file);
            }

            maxPageLabel.setText("/" + pages);
            updateText();
        }

        long endTime = System.currentTimeMillis();

        loadedLabel.setText(loadedLabel.getText() + " in " + (endTime - startTime) + " ms");
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

    }

    public void openDirectory() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(file.getParent()));
        } catch (Exception ex) {
            loadedLabel.setText("invalid file directory");
        }
    }

    public void nextPage() {
        page++;
        updateText();
    }

    public void previousPage() {
        page--;
        updateText();
    }

    public void exportToText() {
        System.out.println("exportToText");
    }

    public void properties() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PropertiesWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Properties");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void about() {
        System.out.println("about");
    }

    public void bookmark() {
        bookmark = BookmarkManager.importBookmark("BookmarkExport.csv");
        bookmark.addBookmark(file, page);
        BookmarkManager.writeToDisk(bookmark.getBookmarkData(), "BookmarkExport.csv");
        System.out.println("bookmarked");
    }

    public void removeBookmark() {
        bookmark.removeBookmark(file);
        BookmarkManager.writeToDisk(bookmark.getBookmarkData(), "BookmarkExport.csv");
    }

    public void exitApplication() {
        Platform.exit();
    }

    public void updateText() {
        if (file != null && file.exists()) {
            int pages = 0;
            //fix text issues
            Scanner scanner = null;
            try {
                pages = reader.getPages();
                scanner = applyTextFormatFixes(reader.readPage(page));
            } catch (IOException e) {
                e.printStackTrace();
            }
            page = wrap(page, 1, pages);

            changePage.setText("" + page);
            mainTextArea.setText("");
            while (scanner.hasNextLine()) {
                mainTextArea.appendText(scanner.nextLine() + "\n");
            }

            loadedLabel.setText("loaded: " + file.getName());
        } else {
            loadedLabel.setText("no file selected");
        }
    }

    public void pageSelected() {
        page = stringToInt(changePage.getText());
        updateText();
    }

    public void openErrorWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ErrorWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Error loading file " + file.getName());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int wrap(Integer value, Integer min, Integer max) {
        int val = value;
        while (val < min) {
            val += max;
        }
        while (val > max) {
            val -= max;
        }
        return val;
    }

    private int stringToInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (IllegalArgumentException e) {
            return page;
        }
    }

    private Scanner applyTextFormatFixes(Scanner scanner) {
        return TextFormatter.formatAll(scanner);
    }

    private void manageOpenBookmarkedSubmenu() {
        bookmark = BookmarkManager.importBookmark("BookmarkExport.csv");
        for (File current : bookmark.getFiles()) {
            MenuItem item = new MenuItem(current.getName());
            item.setOnAction((action) -> openFile(current));
            bookmarkSubmenu.getItems().add(item);
        }
    }
}
