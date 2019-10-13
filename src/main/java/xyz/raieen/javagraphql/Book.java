package xyz.raieen.javagraphql;

import org.springframework.data.annotation.Id;

public class Book {

    @Id
    private String id;
    private String title, author, unusedFieldInMongo; // Notice that there can be unused fields that aren't exposed by graphql.
    private int pageCount;

    private Book() {
    } // For unmarshalling reasons

    public Book(String title, String author, int pageCount) {
        this.title = title;
        this.author = author;
//        this.unusedFieldInMongo = unusedFieldInMongo;
        this.pageCount = pageCount;
    }

    /*
     * Getters/Setters
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getUnusedFieldInMongo() {
        return unusedFieldInMongo;
    }

    public void setUnusedFieldInMongo(String unusedFieldInMongo) {
        this.unusedFieldInMongo = unusedFieldInMongo;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ununsedFieldInMongo='" + unusedFieldInMongo + '\'' +
                ", pageCount=" + pageCount +
                '}';
    }
}
