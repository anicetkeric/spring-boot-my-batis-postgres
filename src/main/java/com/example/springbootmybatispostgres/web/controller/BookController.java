package com.example.springbootmybatispostgres.web.controller;

import com.example.springbootmybatispostgres.common.exception.BadRequestException;
import com.example.springbootmybatispostgres.entities.Book;
import com.example.springbootmybatispostgres.repositories.BookRepository;
import com.example.springbootmybatispostgres.service.BookService;
import com.example.springbootmybatispostgres.web.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

     @PostMapping()
     public ResponseEntity<SuccessResponse> create(@RequestBody @Valid Book book) {
         if (!ObjectUtils.isEmpty(book.getId())) {
             throw new BadRequestException("A new data cannot already have an ID");
         }

         return new ResponseEntity<>(
                 new SuccessResponse(bookService.create(book), "Successful registration"),
                 HttpStatus.CREATED);
     }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAll() {
        List<Book> books = bookService.getAll();

        return new ResponseEntity<>(new SuccessResponse(books, MessageFormat.format("{0} Results found", books.size())), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getOne(@PathVariable("id") Long id) {
        Book book = bookService.getOne(id);
        return new ResponseEntity<>(
                new SuccessResponse(book, "Result found"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(
                new SuccessResponse(null, "Deletion completed successfully"), HttpStatus.OK);
    }


/*    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable long bookId){
        return bookRepository.findById(bookId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("{bookId}")
    public Mono<ResponseEntity<Book>> updateBook(@PathVariable long bookId, @RequestBody Mono<Book> bookMono){
        return bookRepository.findById(bookId)
                .flatMap(book -> bookMono.map(u -> {
                    book.setDescription(u.getDescription());
                    book.setPrice(u.getPrice());
                    book.setIsbn(u.getIsbn());
                    book.setPrice(u.getPrice());
                    book.setPage(u.getPage());
                    return book;
                }))
                .flatMap(bookRepository::save)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable long bookId) {
        return bookRepository.findById(bookId)
                .flatMap(s ->
                        bookRepository.delete(s)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

}