package com.libraryRESTful.Controller;



import com.libraryRESTful.dao.BookDAO;

import com.libraryRESTful.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
public class BookController {
    private BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @PostMapping("/books/createBook")
    public ResponseEntity createUser(@RequestBody Book book){
        return new ResponseEntity(bookDAO.createBook(book), HttpStatus.OK);
    }

    @GetMapping("/books/getBook/{id}")
    public ResponseEntity getUserById(@PathVariable long id){
        return new ResponseEntity(bookDAO.getBookById(id),HttpStatus.OK);
    }

    @DeleteMapping("/books/deleteBook/{id}")
    public ResponseEntity deleteUserById(@PathVariable long id){
        bookDAO.deleteBook(id);
        return new ResponseEntity("Book was deleted",HttpStatus.OK);
    }

    @PutMapping("/books/updateBook/{id}")
    public ResponseEntity updateUser(@PathVariable long id,@RequestBody Book book){
        return  new ResponseEntity(bookDAO.updateBook(id, book),HttpStatus.OK);
    }

    @GetMapping("/books/allBooks")
    public ResponseEntity getAllUsers(){
        return  new ResponseEntity(bookDAO.getAllBooks(),HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity error(){
        return new ResponseEntity("This book in not exist",HttpStatus.BAD_REQUEST);
    }
}
