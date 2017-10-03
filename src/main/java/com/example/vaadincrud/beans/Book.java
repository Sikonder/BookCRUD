package com.example.vaadincrud.beans;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name ="title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "author")
    private String author;
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book(String title, String description, String author, String isbn) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.isbn = isbn;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Book() {
    }
}
