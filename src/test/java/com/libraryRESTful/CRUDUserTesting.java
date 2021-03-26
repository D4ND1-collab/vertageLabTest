package com.libraryRESTful;

import com.libraryRESTful.Repos.UserRepository;
import com.libraryRESTful.model.Book;
import com.libraryRESTful.model.User;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import  static org.junit.Assert.*;


public class CRUDUserTesting {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;
    public static final String ENDPOINT = "/users";
    private static final User newUser = new User();
    public static final Book newBook = new Book();
    private static Long id;

    @Before
    public void setup(){
        id = 1L;
        newUser.setName("User1");
        userRepository.save(newUser);
    }

    /*
    success cases
     */

    @Test
    public void test1UserAddSuccess(){
        ResponseEntity<User> user = restTemplate.postForEntity(ENDPOINT + "/createUser", new HttpEntity<User>(newUser),
                User.class);
        ParamTestFixtures.assertionResponseUser(user, newUser, "test1UserAddSuccess", HttpStatus.CREATED);

    }

    @Test
    public void test2FindUserSuccess(){
        ResponseEntity<User> user = restTemplate.getForEntity(ENDPOINT + "/getUser/" + id, User.class);
        ParamTestFixtures.assertionResponseUser(user, newUser, "test2FindUserSuccess", HttpStatus.OK);
    }

    @Test
    public void test3UpdateUserSuccess(){
        newUser.setName("New Username");
        ResponseEntity<User> user = restTemplate.exchange(ENDPOINT + "/uodateUser/" +id,
                HttpMethod.PUT,
                new HttpEntity<User>(newUser),
                User.class);
        ParamTestFixtures.assertionResponseUser(user, newUser, "test3UpdateUserSuccess",HttpStatus.OK);
    }

    @Test
    public void test4DeleteUserSuccess(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENDPOINT + "/deleteUser/" + id,
                HttpMethod.DELETE,
                null,
                String.class);
        System.out.println("test4DeleteUserSuccess => status response " + responseEntity.getStatusCode() +
                " , expectedStatus: " + HttpStatus.OK);

        assertThat(responseEntity.getStatusCode(),
                is(equalTo(HttpStatus.OK)));

    }

    @Test
    public void test5SelectAllUsers(){
        ResponseEntity<User[]> user = restTemplate.getForEntity(ENDPOINT + "/allUsers", User[].class);

        assertThat(user.getStatusCode(), is(equals(HttpStatus.OK)));
        assertThat(user.getBody().length,
                is(equalTo((int) userRepository.count())));
    }


    /*
    failure cases
     */

    @Test
    public void test1UserAddFailure(){
        ResponseEntity<User> responseEntity = restTemplate.exchange(ENDPOINT + "/addUser",
                HttpMethod.POST,
                null,
                User.class);

        ParamTestFixtures.assertionResponseFailure(responseEntity, "test1UserAddFailure", HttpStatus.CREATED);
    }

    @Test
    public void test2FindUserFailure(){
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(ENDPOINT + "/getUser/" + id,
                User.class);

        ParamTestFixtures
                .assertionResponseFailure(responseEntity, "test2FindUserFailure", HttpStatus.OK);
    }

    @Test
    public void test3UpdateUserFailure(){
        newUser.setName("UpdatedName");
        ResponseEntity<User> responseEntity = restTemplate.exchange(ENDPOINT + "/updateUser/" + id,
                HttpMethod.PUT,
                new HttpEntity<User>(newUser),
                User.class);
    }

    @Test
    public void test4DeleteUserFailure(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENDPOINT + "/deleteUser/" + id,
                HttpMethod.DELETE,
                null,
                String.class);
        ParamTestFixtures.assertionResponseFailure(responseEntity, "test4DeleteUserFailure", HttpStatus.OK);
    }

}
