package ba.sum.fsre.knjiznica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 200, nullable = false)
    @NotBlank(message = "Molimo unesite naslov knjige.")
    String title;

    @Column(length = 150, nullable = false)
    @NotBlank(message = "Molimo unesite autora knjige.")
    String author;

    @Column(length = 30, nullable = false)
    @NotBlank(message = "Molimo unesite ISBN knjige.")
    String ISBN;

    @Column(length = 5, nullable = false)
    @NotBlank(message = "Molimo unesite godinu izdanja knjige.")
    String year;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
