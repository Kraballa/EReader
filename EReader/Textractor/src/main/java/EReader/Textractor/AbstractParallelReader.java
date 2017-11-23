package EReader.Textractor;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Implements most methods from the BasicReader Interface. If you want to expand this program with more FileReader,
 * extend from this class and implement loadPage() and getfPages().
 */
public abstract class AbstractParallelReader implements BasicReader{
    private String[] content;
    private File file;
    private int numOfProcessors;

    public AbstractParallelReader(){
        setNumOfProcessors(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void loadFile(File file) throws IOException {
        this.file = file;
        int pages = getPages();
        if (pages <= 0) {
            throw new IOException("Error, unable to fetch file head.");
        }
        content = new String[getPages()];
        PageLoader[] threads = new PageLoader[numOfProcessors];

        for(int i = 0; i < numOfProcessors; i++){
            int pagesInThread = pages / numOfProcessors;
            if(i == 0){
                pagesInThread += pagesInThread %numOfProcessors;
            }

            threads[i] = new PageLoader(i,pagesInThread);
            threads[i].start();
        }

        for(PageLoader thread: threads){
            try{
                thread.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            addToContent(thread.getText(),thread.getPage());
        }
    }

    @Override
    public Scanner readPage(Integer page) throws IOException {
        if (content != null){
            if(content[page-1] != null) {
                return new Scanner(content[page - 1]);
            }else{
                if(file != null && file.exists()){
                    content[page-1] = loadPage(page);
                    return readPage(page);
                }else{
                    throw new IOException("Error, no File has been loaded yet.");
                }
            }
        }else{
            throw new IOException("Error, no File has been loaded yet.");
        }
    }

    public File getFile() {
        return file;
    }

    private void setNumOfProcessors(int numOfProcessors){
        if(numOfProcessors > Runtime.getRuntime().availableProcessors()){
            this.numOfProcessors = Runtime.getRuntime().availableProcessors();
        }else if(numOfProcessors < 1){
            this.numOfProcessors = 1;
        }else{
            this.numOfProcessors = numOfProcessors;
        }
    }

    @Override
    public String getExtension() {
        return null;
    }

    private synchronized void addToContent(String[] text, Integer startIndex){
        if(content != null && text != null){
            for(int i = 0; i < text.length; i++){
                try{
                    content[i+startIndex] = text[i];
                }catch(ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }else{
            throw new IllegalArgumentException("Illegal arguments");
        }
    }

    /**
     * Private class to load the pages via multithreading
     */
    private class PageLoader extends Thread{

        private File file;
        private int page;
        private int pagesInThread;
        private String[] text;

        private PageLoader(Integer page, Integer pagesInThread){
            this.file = getFile();
            this.page = page;
            this.pagesInThread = pagesInThread;
            text = new String[pagesInThread];
        }


        public String[] getText() {
            return text;
        }

        public int getPage() {
            return page;
        }

        @Override
        public void run(){
            for(int i = 0; i < pagesInThread; i++){
                try {
                    text[i] = loadPage(i + page);
                } catch (IOException e) {
                    e.printStackTrace();
                    text[i] = "Error loading page.";
                }
            }
        }
    }
}