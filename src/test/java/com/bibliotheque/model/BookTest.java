package com.bibliotheque.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    @DisplayName("les attributs du livre sont bien restitues")
    void attributsRestitues() {
        Book livre = new Book("1", "Le Petit Prince", "Saint-Exupery", "978-2070612758");

        assertEquals("1", livre.getId());
        assertEquals("Le Petit Prince", livre.getTitle());
        assertEquals("Saint-Exupery", livre.getAuthor());
        assertEquals("978-2070612758", livre.getIsbn());
    }
}
