package com.decagon.work.implementation1.service;

import com.decagon.work.implementation1.model.Book;
import com.decagon.work.implementation1.model.Person;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class LibraryServiceImplementation implements LibraryService{
    private final HashMap<String, Queue<Book>> books;
    private final HashMap<String, PriorityQueue<Person>> bookRequests;
    private final HashMap<Book, Person> borrowedBooks;

    public LibraryServiceImplementation() {
        this.books = new HashMap<>();
        this.bookRequests = new HashMap<>();
        this.borrowedBooks = new HashMap<>();
    }

    @Override
    public void addBook(Book book) {
     books.putIfAbsent(book.getTitle(), new LinkedList<>());
     books.get(book.getTitle()).add(book);
    }

    @Override
    public void addPersonToQueue(Person person) {
        String requestedBookTitle = person.getRequestedBook().getTitle();
        bookRequests.putIfAbsent(requestedBookTitle, new PriorityQueue<>(new RoleComparator()));
        bookRequests.get(requestedBookTitle).add(person);

    }

    @Override
    public void borrowBooks() {
        for(String bookTitle : books.keySet()){
            Queue<Book>bookQueue = books.get(bookTitle);
            PriorityQueue<Person> requestQueue = bookRequests.get(bookTitle);
            while(!bookQueue.isEmpty() && requestQueue != null && !requestQueue.isEmpty()){
                Book book = bookQueue.poll();
                Person person = requestQueue.poll();
                borrowedBooks.put(book, person);
                assert person != null;
                System.out.println(person.getName() + " has borrowed " + book);

                if(requestQueue.isEmpty()){
                    bookRequests.remove(bookTitle);
                }
            }
        }

    }

    @Override
    public boolean returnBook(Book book) {

        Person person = borrowedBooks.remove(book);
        if(person == null)
            return false;
        books.get(book.getTitle()).add(book);
        return true;
    }

    public Queue<Book> getBooks(){
        Queue<Book> allBooks = new LinkedList<>();
        for(Queue<Book> bookQueue : books.values()){
            allBooks.addAll(bookQueue);
        }
        return allBooks;
    }

    public HashMap<String, PriorityQueue<Person>> getBookRequests(){
        return bookRequests;
    }
 public boolean isBookBorrowedBy(Person person, Book book){
        return borrowedBooks.get(book) != null && borrowedBooks.get(book).equals(person);
 }
}



