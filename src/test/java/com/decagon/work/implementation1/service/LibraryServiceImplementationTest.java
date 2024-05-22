package com.decagon.work.implementation1.service;

import com.decagon.work.enums.Role;
import com.decagon.work.implementation1.model.Book;
import com.decagon.work.implementation1.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.PriorityQueue;
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
        Queue<Book> books =((LibraryServiceImplementation)libraryService).getBooks();
        assertEquals(1, books.size());
    }

    @Test
    void addPersonToQueue() {
        Book book = new Book("Into to Testing with JUnit5", "Uzoma Ibezim");
        Person person1 = new Person("Okoro Uchechukwu", Role.SENIOR_STUDENT, book);
        Person person2 = new Person("John Ideba", Role.SENIOR_STUDENT, book);

        libraryService.addPersonToQueue(person1);

        HashMap<String, PriorityQueue<Person>> bookRequests = ((LibraryServiceImplementation)libraryService).getBookRequests();
        assertEquals(1, bookRequests.get(book.getTitle()).size());
        assertEquals(person1, bookRequests.get(book.getTitle()).peek());
        assertNotEquals(person2, bookRequests.get(book.getTitle()).peek());
    }

    @Test
    void borrowBooks() {
        Book book1 = new Book("Intro to JAVA", "James Gosling");
        Book book2 = new Book("Intro to Testing with JUnit5", "Uzoma Ibezim");
        libraryService.addBook(book1);
        libraryService.addBook(book2);

        Person teacher = new Person("Segun Osiki", Role.TEACHER, book1);
        Person seniorStudent = new Person("Okoro Uchechukwu", Role.SENIOR_STUDENT, book1);

        Person juniorStudent = new Person("Okereke Ifeoma", Role.JUNIOR_STUDENT, book2);
        Person seniorStudent2 = new Person("Ideba John", Role.SENIOR_STUDENT, book2);

        libraryService.addPersonToQueue(seniorStudent);
        libraryService.addPersonToQueue(teacher);

        libraryService.addPersonToQueue(juniorStudent);
        libraryService.addPersonToQueue(seniorStudent2);

        libraryService.borrowBooks();
        assertTrue(((LibraryServiceImplementation) libraryService).isBookBorrowedBy(teacher, book1));
        assertFalse(((LibraryServiceImplementation) libraryService).isBookBorrowedBy(seniorStudent, book1));



        assertFalse(((LibraryServiceImplementation) libraryService).isBookBorrowedBy(juniorStudent, book2));
        assertTrue(((LibraryServiceImplementation)libraryService).isBookBorrowedBy(seniorStudent2, book2));
    }

    @Test
    void returnBook() {
        Book book = new Book("Data Science", "Decagon");
        libraryService.addBook(book);

        Person teacher = new Person("Segun Osiki", Role.TEACHER, book);
        libraryService.addPersonToQueue(teacher);
        libraryService.borrowBooks();

        assertTrue(((LibraryServiceImplementation)libraryService).isBookBorrowedBy(teacher, book));

        assertTrue(libraryService.returnBook(book));
        assertFalse(((LibraryServiceImplementation)libraryService).isBookBorrowedBy(teacher,book));

        Book nonBorrowedBook = new Book("Intro to Information Technology", "Gadibia Daro");
        assertFalse(libraryService.returnBook(nonBorrowedBook));

    }
}