package com.decagon.work.implementation2.service;

import com.decagon.work.enums.Role;
import com.decagon.work.implementation2.model.Book;
import com.decagon.work.implementation2.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceImplementationTest {
    private LibraryService libraryService;

    @BeforeEach
    void setUp(){
        libraryService = new LibraryServiceImplementation();
    }

    @Test
    void addBook() {
        Book book = new Book("Intro to JAVA", "James Gosling");
        libraryService.addBook(book);

        Queue<Book> books = ((LibraryServiceImplementation)libraryService).getBooks();
        assertEquals(1, books.size());
        assertEquals(book, books.peek());
    }

    @Test
    void addPersonToQueue() {
        Book book = new Book("Intro to Java", "James Gosling");
        Person person = new Person("Okoro Uchechukwu", 26, Role.SENIOR_STUDENT,book);
        libraryService.addPersonToQueue(person);

        Queue<Person> bookRequests = ((LibraryServiceImplementation)libraryService).getBookRequests();
        assertEquals(person, bookRequests.peek());
    }

    @Test
    void borrowBooks() {
        Book book1 = new Book("Intro to Java", "James Gosling");
        Book book2 = new Book("Intro to testing with JUnit5", "Uzoma Ibezim");

        libraryService.addBook(book1);
        libraryService.addBook(book2);

        Person student1 = new Person("Okereke Ifeoma", 28, Role.JUNIOR_STUDENT, book1);
        Person teacher = new Person("Segun Osiki", 35, Role.TEACHER, book1);
        Person student2 = new Person("Okoro Uchechukwu", 26, Role.SENIOR_STUDENT, book2);
        Person student3 = new Person("John Ideba", 18, Role.SENIOR_STUDENT,book2);

        libraryService.addPersonToQueue(student1);
        libraryService.addPersonToQueue(teacher);
        libraryService.addPersonToQueue(student2);
        libraryService.addPersonToQueue(student3);

        libraryService.borrowBooks();

        assertTrue(((LibraryServiceImplementation)libraryService).isBookBorrowedBy(student1, book1));
        assertFalse(((LibraryServiceImplementation)libraryService).isBookBorrowedBy(teacher, book1));
        assertTrue(((LibraryServiceImplementation)libraryService).isBookBorrowedBy(student2, book2));

    }

    @Test
    void returnBook() {
        Book book = new Book("Data Science", "Decagon");
        libraryService.addBook(book);
        Person teacher = new Person("Segun Osiki", 35, Role.TEACHER, book);
        libraryService.addPersonToQueue(teacher);
        libraryService.borrowBooks();

        assertTrue(((LibraryServiceImplementation)libraryService).isBookBorrowedBy(teacher, book));
        assertTrue(libraryService.returnBook(book));
        assertFalse(((LibraryServiceImplementation)libraryService).isBookBorrowedBy(teacher, book));

        Book nonBorrowedBook = new Book("Intro to Information Technology", "Gadibia Daro");
        assertFalse(libraryService.returnBook(nonBorrowedBook));
    }
}