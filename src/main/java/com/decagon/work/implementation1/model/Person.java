package com.decagon.work.implementation1.model;

import com.decagon.work.enums.Role;

public class Person {
    private final String name;
    private final Role role;
    private final Book requestedBook;

    public Person(String name, Role role, Book requestedBook) {
        this.name = name;
        this.role = role;
        this.requestedBook = requestedBook;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public Book getRequestedBook() {
        return requestedBook;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", role=" + role +
                ", requestedBook=" + requestedBook +
                '}';
    }
}
