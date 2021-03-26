package com.libraryRESTful;

import com.libraryRESTful.Repos.BooksRepository;
import com.libraryRESTful.model.Book;
import com.libraryRESTful.model.Genres;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CRUDBookTesting {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    BooksRepository booksRepository;
    public static final String ENDPOINT = "/users";
    public static final Book newBook = new Book();
    public static Long id;

    @Before
    public void setup(){
        id = 1L;
        newBook.setId(id);
        newBook.setName("book1");
        newBook.setAuthor("author1");
        newBook.setGenre(Genres.BIOGRAPHY);
        newBook.setIsbn(25);
        booksRepository.save(newBook);
    }

    /*
    successful cases
     */

    @Test
    public void test1AddBookSuccess(){
        ResponseEntity<Book> book = testRestTemplate.postForEntity(ENDPOINT
                + "/addBook",
                new HttpEntity<Book>(newBook),
                Book.class);
        ParamTestFixtures.assertationResponseBook(book, newBook, "test1AddBookSuccess", HttpStatus.OK);
    }

    @Test
    public void test2FindBookSuccess(){
        ResponseEntity<Book> bookResponseEntity = testRestTemplate.getForEntity(ENDPOINT
                + "/getBook/" + id,
                Book.class);
        ParamTestFixtures.assertationResponseBook(bookResponseEntity, newBook, "test2FindBookSuccess", HttpStatus.OK);
    }

    @Test
    public void test3UpdateBookSuccess(){
        newBook.setName("new name");
        newBook.setAuthor("new author");
        newBook.setGenre(Genres.DETECTIVE);
        newBook.setIsbn(24);
        ResponseEntity<Book> book = testRestTemplate.exchange(ENDPOINT + "/updateBook/" + id,
                HttpMethod.PUT,
                new HttpEntity<Book>(newBook),
                Book.class);
        ParamTestFixtures.assertationResponseBook(book, newBook, "test3UpdateBookSuccess", HttpStatus.OK);
    }

    @Test
    public void test4DeleteBookSuccess(){

            ResponseEntity<String> response = testRestTemplate.exchange(ENDPOINT + "/deleteBook/" + id,
                    HttpMethod.DELETE,
                    null,
                    String.class);
            System.out.println("test4DeleteBookSuccess=> status response " + response.getStatusCode()+ " , expectedStatus :"+HttpStatus.OK);

            assertThat(response.getStatusCode(),
                    is(equalTo(HttpStatus.OK)));
        }

    @Test
    public void test5SelectAllBooksSuccess(){
        ResponseEntity<Book[]> books = testRestTemplate.getForEntity(ENDPOINT + "/allBooks",
                Book[].class);

        assertThat(books.getStatusCode(),
                is(equalTo(HttpStatus.OK)));
        assertThat(books.getBody().length,
                is(equalTo((int)booksRepository.count())));
    }

    /*
    failed cases
     */

    @Test
    public void test1AddBookFail(){
        ResponseEntity<Book> responseEntity = testRestTemplate.exchange(ENDPOINT +
                "/addBook", HttpMethod.POST,
                null,
                Book.class);

        ParamTestFixtures.assertionResponseFailure(responseEntity, "test1AddBookFail", HttpStatus.OK);
    }

    @Test
    public void test2FindBookFail(){
        ResponseEntity<Book> responseEntity = testRestTemplate.getForEntity(ENDPOINT +
                "/getBook/" + id,
                Book.class);
        ParamTestFixtures.assertionResponseFailure(responseEntity, "test2FindBookFail", HttpStatus.OK);
    }

    @Test
    public void test3UpdateBookFail(){
        newBook.setName("new name");
        newBook.setAuthor("new author");
        newBook.setGenre(Genres.DETECTIVE);
        newBook.setIsbn(24);
        ResponseEntity<Book> responseEntity = testRestTemplate.exchange(ENDPOINT +
                "/updateBook/" + id,
                HttpMethod.PUT,
                new HttpEntity<Book>(newBook),
                Book.class);
        ParamTestFixtures.assertionResponseFailure(responseEntity, "test3UpdateBookFail", HttpStatus.OK);
    }

    @Test
    public void test4DelBookFail(){
        ResponseEntity<Book> responseEntity = testRestTemplate.exchange(ENDPOINT +
                "/deleteBook/" + id,
                HttpMethod.DELETE,
                null,
                Book.class);
        ParamTestFixtures.assertionResponseFailure(responseEntity, "test4DelBookFail", HttpStatus.OK);
    }

    }

