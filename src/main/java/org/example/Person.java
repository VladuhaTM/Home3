package org.example;

import lombok.Data;
import java.util.List;

@Data
class Person {
    String name;
    String surname;
    String phone;
    List<Book> favoriteBooks;
    boolean subscribed;
}