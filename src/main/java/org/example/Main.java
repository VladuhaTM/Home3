package org.example;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("src/main/java/books.json");
            Gson gson = new Gson();
            Person[] visitors = gson.fromJson(reader, Person[].class);

            //1
            List<Person> visitorList = List.of(visitors);
            System.out.println("Задание 1:");
            System.out.println("Посетители:");
            visitorList.forEach(System.out::println);
            System.out.println("Кол-во посетителей: " + visitorList.size());
            System.out.println();

            //2
            Set<Book> uniqueBooks = visitorList.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .collect(Collectors.toSet());
            System.out.println("Задание 2:");
            System.out.println("Уникальные книги:");
            uniqueBooks.forEach(System.out::println);
            System.out.println("Всего уникальных книг: " + uniqueBooks.size());
            System.out.println();

            //3
            List<Book> sortedBooks = uniqueBooks.stream()
                    .sorted(Comparator.comparingInt(Book::getPublishingYear))
                    .toList();
            System.out.println("Задание 3:");
            System.out.println("Книги отсортированы по году издания:");
            sortedBooks.forEach(System.out::println);
            System.out.println();

            //4
            boolean hasBook = visitorList.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .anyMatch(book -> "Jane Austen".equals(book.getAuthor()));
            System.out.println("Задание 4:");
            System.out.println("Есть ли какая-нибудь книга от Джейна Остина: " + hasBook);
            System.out.println();

            //5
            int maxFavoriteBooks = visitorList.stream()
                    .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                    .max()
                    .orElse(0);
            System.out.println("Задание 5:");
            System.out.println("Максимальное количество любимых книг на одного посетителя: " + maxFavoriteBooks);
            System.out.println();

            //6
            double averageBooks = visitorList.stream()
                    .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                    .average()
                    .orElse(0);

            List<SMS> messages = visitorList.stream()
                    .filter(Person::isSubscribed)
                    .map(visitor -> {
                        int bookCount = visitor.getFavoriteBooks().size();
                        String message;
                        if (bookCount > averageBooks) {
                            message = "you are a bookworm";
                        } else if (bookCount < averageBooks) {
                            message = "read more";
                        } else {
                            message = "fine";
                        }
                        return new SMS(visitor.getPhone(), message);
                    })
                    .toList();

            System.out.println("Задание 6:");
            System.out.println("SMS сообщения для подписчиков:");
            messages.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл 'books.json' не найден.");
        }
    }
}

class SMS {
    private final String phoneNumber;
    private final String message;

    public SMS(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Номер: " + phoneNumber + ", Сообщение: '" + message + "'";
    }
}