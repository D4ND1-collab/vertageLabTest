package com.libraryRESTful.Controller;



import com.libraryRESTful.dao.UserDAO;
import com.libraryRESTful.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
public class UserController {
    private UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("/users/createUser")
    public ResponseEntity createUser(@RequestBody User user){
        return new ResponseEntity(userDAO.createUser(user),HttpStatus.OK);
    }

    @GetMapping("/users/getUser/{id}")
    public ResponseEntity getUserById(@PathVariable long id){
        return new ResponseEntity(userDAO.getUserById(id),HttpStatus.OK);
    }

    @DeleteMapping("/users/deleteUser/{id}")
    public ResponseEntity deleteUserById(@PathVariable long id){
        userDAO.deleteUser(id);
        return new ResponseEntity("Пользователь удален",HttpStatus.OK);
    }

    @PutMapping("/users/updateUser/{id}")
    public ResponseEntity updateUser(@PathVariable long id,@RequestBody User user){
        return  new ResponseEntity(userDAO.updateUser(id, user),HttpStatus.OK);
    }

    @GetMapping("/users/allUsers")
    public ResponseEntity getAllUsers(){
        return  new ResponseEntity(userDAO.getAllUsers(),HttpStatus.OK);
    }

    @PostMapping("/users/takeBook/{bookID}/{userID}")
    public ResponseEntity takeBook(@PathVariable long book_id,@PathVariable long user_id){
        return new ResponseEntity(userDAO.takeBook(book_id, user_id),HttpStatus.OK);
    }

    @PostMapping("/users/returnBook/{bookID}/{userID}")
    public ResponseEntity returnBook(@PathVariable long book_id,@PathVariable long user_id){
        return new ResponseEntity(userDAO.returnBook(book_id, user_id),HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity error(){
        return new ResponseEntity("Такого пользователя не существует", HttpStatus.BAD_REQUEST);
    }

}
