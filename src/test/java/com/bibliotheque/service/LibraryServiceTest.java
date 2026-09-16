package com.bibliotheque.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bibliotheque.model.Book;
import com.bibliotheque.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LibraryServiceTest {

    @Test
    @DisplayName("un livre neuf est disponible")
    void livreNeufEstDisponible() {
        Book livre = new Book("1", "Le Petit Prince", "Saint-Exupery", "978-2070612758");
        assertTrue(livre.isAvailable());
    }

    @Test
    @DisplayName("un livre emprunte n est plus disponible")
    void livreEmprunteNEstPlusDisponible() {
        Book livre = new Book("2", "L Etranger", "Camus", "978-2070360024");
        livre.setAvailable(false);
        assertFalse(livre.isAvailable());
    }

    @Test
    @DisplayName("le titre saisi est bien celui restitue")
    void titreRestitue() {
        Book livre = new Book("3", "Germinal", "Zola", "978-2070413027");
        assertEquals("Germinal", livre.getTitle());
    }

    // --- Tests de LibraryService ---

    private LibraryService library;

    @BeforeEach
    void setUp() {
        library = new LibraryService(new NotificationService());
        library.addBook(new Book("1", "Le Petit Prince", "Saint-Exupery", "978-2070612758"));
        library.addBook(new Book("2", "L Etranger", "Camus", "978-2070360024"));
        library.addMember(new Member("m1", "Ada Lovelace", "ada@example.com"));
    }

    @Test
    @DisplayName("emprunter un livre disponible le rend indisponible")
    void emprunterRendLivreIndisponible() {
        library.borrowBook("1", "m1");
        assertFalse(library.findBookById("1").orElseThrow().isAvailable());
    }

    @Test
    @DisplayName("rendre un livre le remet disponible")
    void rendreRemetLivreDisponible() {
        library.borrowBook("1", "m1");
        library.returnBook("1");
        assertTrue(library.findBookById("1").orElseThrow().isAvailable());
    }

    @Test
    @DisplayName("emprunter un livre deja emprunte leve une exception")
    void emprunterLivreDejaEmprunteEchoue() {
        library.borrowBook("1", "m1");
        assertThrows(IllegalStateException.class, () -> library.borrowBook("1", "m1"));
    }

    @Test
    @DisplayName("la recherche par titre trouve le livre correspondant")
    void rechercheParTitreTrouveLeLivre() {
        assertEquals(1, library.searchByTitle("petit prince").size());
    }

    @Test
    @DisplayName("la liste des livres disponibles exclut les livres empruntes")
    void listeDisponiblesExcluLivresEmpruntes() {
        library.borrowBook("1", "m1");
        assertEquals(1, library.listAvailableBooks().size());
    }
}
