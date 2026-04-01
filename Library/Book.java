package Library;

public class Book extends LibraryItem{
    private int pageCount;
    private String author;

    public Book(int id, String title, int year, String author, int pageCount) {
        super(id, title, year);
        this.author = author;
        this.pageCount = pageCount;
    }

    @Override
    public String getDescription() {
        return "";
    }

}
