package pkgData;

public class Book {

    private int id;
    private String title = null,
            author = null,
            nameOfImage = null;

    public Book() {
        this(-99, "no book", "no authors");
    }

    public Book(int id, String title, String author) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNameOfImage() {
        return nameOfImage;
    }

    public void setNameOfImage(String nameOfImage) {
        this.nameOfImage = nameOfImage;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title=" + title + ", author=" + author + ", nameOfImage=" + nameOfImage + '}';
    }

}
