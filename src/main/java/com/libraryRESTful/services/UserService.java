package com.libraryRESTful.services;

import com.libraryRESTful.Repos.BooksRepository;
import com.libraryRESTful.Repos.UserRepository;
import com.libraryRESTful.dao.UserDAO;
import com.libraryRESTful.model.Book;
import com.libraryRESTful.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService implements UserDAO {
    private UserRepository repository;
    private BooksRepository bookRepository;

    @Autowired
    public UserService(UserRepository repository, BooksRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @Override
    public User createUser(User user) {
        return repository.save(user);
    }

    @Override
    public String deleteUser(long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            repository.delete(user.get());
        } else throw new NoSuchElementException();
        return "Пользователь по ID : " + id + " удален";
    }

    @Override
    public User updateUser(long id, User user) {
        User updateUser = repository.findById(id).get();
        updateUser.setName(user.getName());
        return repository.save(updateUser);
    }

    @Override
    public User getUserById(long id) {
        return repository.findById(id).get();
    }


    @Override
    public List<User> getAllUsers() {
        return (List<User>) repository.findAll();
    }

    @Override
    public String takeBook(long bookID, long userID) {
        Book book = bookRepository.findById(bookID).get();
        User user = repository.findById(userID).get();
        List<User> users = (List<User>) repository.findAll();

        boolean isPresent = users.stream().anyMatch(u -> u.getBooks().stream().anyMatch(b -> b.getId() == book.getId()));
        if (isPresent) {
            return "Книга уже взята!";
        }

        user.getBooks().add(book);
        repository.save(user);
        return "Взяли книгу: " + book.getName() ;
    }

    @Override
    public String returnBook(long book_id, long user_id) {
        Book book = bookRepository.findById(book_id).get();
        User user = repository.findById(user_id).get();
        user.getBooks().remove(book);
        repository.save(user);
        return "Вернули книгу: " + book.getName() ;
    }
}