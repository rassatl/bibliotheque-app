package com.bibliotheque.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LoanTest {

    @Test
    @DisplayName("un emprunt neuf n est pas rendu et conserve ses attributs")
    void empruntNeufNEstPasRendu() {
        LocalDate date = LocalDate.of(2026, 1, 10);
        Loan emprunt = new Loan("1", "m1", date);

        assertEquals("1", emprunt.getBookId());
        assertEquals("m1", emprunt.getMemberId());
        assertEquals(date, emprunt.getLoanDate());
        assertNull(emprunt.getReturnDate());
        assertFalse(emprunt.isReturned());
    }

    @Test
    @DisplayName("marquer un emprunt comme rendu met a jour la date de retour")
    void marquerRenduMetAJourLaDateDeRetour() {
        Loan emprunt = new Loan("1", "m1", LocalDate.of(2026, 1, 10));
        LocalDate dateRetour = LocalDate.of(2026, 1, 20);

        emprunt.markReturned(dateRetour);

        assertEquals(dateRetour, emprunt.getReturnDate());
        assertTrue(emprunt.isReturned());
    }
}
