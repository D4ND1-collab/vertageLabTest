package com.libraryRESTful;

import com.libraryRESTful.model.Book;
import com.libraryRESTful.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ParamTestFixtures {
    private static Logger log = LoggerFactory.getLogger("ParamTestFixtures");

    public static void assertionResponseUser(ResponseEntity<User> response, User userMock, String testName, HttpStatus expectedStatus) {
        String info="user(id=" + userMock.getId()!=null?String.valueOf(userMock.getId()):"" + ") " + testName+"=> status response :"
                + response.getStatusCode()
                + ", expectedStatus :" + expectedStatus;
        log.info(info);

        assertThat(response.getStatusCode(), is(equalTo(expectedStatus)));
        assertThat(response.getBody().getName(),
                is(equalTo(userMock.getName())));

    }

    public static void assertionResponseFailure(ResponseEntity response, String testName,HttpStatus expectedStatus) {
        String info= testName +"=> status response :"
                + response.getStatusCode()
                + ", expectedStatus :" + expectedStatus;
        log.info(info);
        assertThat(response.getStatusCode(),
                not(equalTo(expectedStatus)));
    }

    public static void assertationResponseBook(ResponseEntity<Book> responseEntity, Book bookMock, String testName, HttpStatus expectedStatus){
        String info = "book(id=" + bookMock.getId()!=null?String.valueOf(bookMock.getId()):""+") " + testName+"=> status response :"
                + responseEntity.getStatusCode()
                + ", expectedStatus :" + expectedStatus;
        log.info(info);

        assertThat(responseEntity.getStatusCode(), is(equalTo(expectedStatus)));
        assertThat(responseEntity.getBody(),
                is(equalTo(bookMock.getName())));
        assertThat(responseEntity.getBody(),
                is(equalTo(bookMock.getAuthor())));
        assertThat(responseEntity.getBody(),
                is(equalTo(bookMock.getGenre())));
        assertThat(responseEntity.getBody(),
                is(equalTo(bookMock.getIsbn())));

    }


}
