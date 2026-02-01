package com.example.springbootmybatispostgres.service;

import com.example.springbootmybatispostgres.common.exception.DataNotFoundException;
import com.example.springbootmybatispostgres.common.exception.DuplicateException;
import com.example.springbootmybatispostgres.entities.Book;
import com.example.springbootmybatispostgres.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public Book create(Book book) {
        if (repository.findByTitle(book.getTitle()) != null) {
            throw new DuplicateException(
                    MessageFormat.format(
                            "Book {0} already exists in the system",
                            book.getTitle()
                    )
            );
        }

        repository.insert(book);
        return repository.findByTitle(book.getTitle());
    }

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public Book getOne(long id) {
        Book book = repository.findById(id);
        if (book == null) {
            throw new DataNotFoundException(
                    MessageFormat.format("Book id {0} not found", id)
            );
        }
        return book;
    }

    @Override
    public void deleteById(long id) {
        if (repository.findById(id) == null) {
            throw new DataNotFoundException(
                    MessageFormat.format("Book id {0} not found", id)
            );
        }

        repository.deleteById(id);
    }

    @Override
    public Book getByTitle(String title) {
        return repository.findByTitle(title);
    }
}
