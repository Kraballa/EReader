package EReader.Textractor;

import java.io.File;
import java.util.Scanner;

public abstract class AbstractParallelReader implements BasicReader{
    private String[] content;
    private File file;
    private int numOfProcessors;

    public AbstractParallelReader(){
        setNumOfProcessors(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Loads an entire File. This incorporates opening the document, extracting the text and storing the data in a
     * Scanner[] for every page.
     * @param file  the file to load
     */
    public void loadFile(File file){
        this.file = file;
        content = new String[getPages()];
        PageLoader[] threads = new PageLoader[numOfProcessors];

        for(int i = 0; i < numOfProcessors; i++){
            int pagesInThread = getPages()/numOfProcessors;
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
                text[i] = loadPage(i+page);
            }
        }
    }
}