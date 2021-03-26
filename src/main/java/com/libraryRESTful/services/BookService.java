package com.libraryRESTful.services;

import com.libraryRESTful.Repos.BooksRepository;
import com.libraryRESTful.dao.BookDAO;
import com.libraryRESTful.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService implements BookDAO {
    private BooksRepository repository;

    @Autowired
    public BookService(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book createBook(Book book) {
        return repository.save(book);
    }



    @Override
    public Book updateBook(long id, Book book) {
        Book updateBook = repository.findById(id).get();
        updateBook.setName(book.getName());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setIsbn(book.getIsbn());
        updateBook.setGenre(book.getGenre());
        return repository.save(updateBook);
    }

    @Override
    public Book getBookById(long id) {
        return repository.findById(id).get();
    }

    @Override
    public String deleteBook(long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isPresent()) {
            repository.delete(book.get());
        } else throw new NoSuchElementException();
        return "Книга удалена поID: " + id ;
    }

    @Override
    public Iterable<Book> getAllBooks() {
        return repository.findAll();
    }
}