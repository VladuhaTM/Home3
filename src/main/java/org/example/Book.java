package org.example;

import lombok.Data;

@Data
class Book {
    String name;
    String author;
    int publishingYear;
    String isbn;
    String publisher;
}
