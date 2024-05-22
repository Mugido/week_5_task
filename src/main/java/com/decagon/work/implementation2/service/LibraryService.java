package com.decagon.work.implementation2.service;

import com.decagon.work.implementation2.model.Book;
import com.decagon.work.implementation2.model.Person;

public interface LibraryService {
    void addBook(Book book);
    void addPersonToQueue(Person person);
    void borrowBooks();
    boolean returnBook(Book book);

}
