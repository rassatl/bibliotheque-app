package com.bibliotheque;

import com.bibliotheque.model.Book;
import com.bibliotheque.model.Member;
import com.bibliotheque.service.LibraryService;
import com.bibliotheque.service.NotificationService;

public class Main {

    public static void main(String[] args) {
        LibraryService library = new LibraryService(new NotificationService());

        library.addBook(new Book("1", "Le Petit Prince", "Saint-Exupery", "978-2070612758"));
        library.addBook(new Book("2", "L'Etranger", "Camus", "978-2070360024"));
        library.addMember(new Member("m1", "Ada Lovelace", "ada@example.com"));

        System.out.println(library.borrowBook("1", "m1"));
        System.out.println(library.returnBook("1"));
    }
}
