package com.example.vaadincrud.repository;

import com.example.vaadincrud.beans.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long>{

    Book findByTitleStartsWithIgnoreCase(String filterText);
}
