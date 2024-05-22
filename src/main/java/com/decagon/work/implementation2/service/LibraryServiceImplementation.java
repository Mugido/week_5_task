package com.decagon.work.implementation2.service;

import com.decagon.work.implementation2.model.Book;
import com.decagon.work.implementation2.model.Person;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class LibraryServiceImplementation implements LibraryService{

    private HashMap<String, Queue<Book>> books;
    private Queue<Person> bookRequests;
    private HashMap<Book, Person> borrowedBooks;

    public LibraryServiceImplementation() {
        this.books = new HashMap<>();
        this.bookRequests = new LinkedList<>();
        this.borrowedBooks = new HashMap<>();
    }

    @Override
    public void addBook(Book book) {
        books.putIfAbsent(book.getTitle(), new LinkedList<>());
        books.get(book.getTitle()).add(book);

    }

    @Override
    public void addPersonToQueue(Person person) {
        bookRequests.add(person);

        }



    @Override
    public void borrowBooks() {
        while(!bookRequests.isEmpty()){
            Person person = bookRequests.poll();
            String requestBookTitle = person.getRequestedBook().getTitle();
            Queue<Book> bookQueue = books.get(requestBookTitle);

            if(bookQueue != null && !bookQueue.isEmpty()){
                Book book = bookQueue.poll();
                borrowedBooks.put(book,person);
                System.out.println(person + " has borrowed " + book);
            }else {
                System.out.println("No copies of " + requestBookTitle + "are available for " + person);
            }
        }

    }

    @Override
    public boolean returnBook(Book book) {
        if(borrowedBooks.containsKey(book)){
            Person person = borrowedBooks.remove(book);
            books.get(book.getTitle()).add(book);
            System.out.println(person + "has returned " + book);
            return true;
        }else{
            System.out.println("This book was not borrowed from this library");
            return false;
        }

    }

    public Queue<Book> getBooks(){
        Queue<Book> allBooks = new LinkedList<>();
        for(Queue<Book> bookQueue : books.values()){
            allBooks.addAll(bookQueue);
        }
        return allBooks;
    }
    public Queue<Person> getBookRequests(){
        return new LinkedList<>(bookRequests);
    }
    public boolean isBookBorrowedBy(Person person, Book book){
        return borrowedBooks.get(book) != null && borrowedBooks.get(book).equals(person);
    }
}
