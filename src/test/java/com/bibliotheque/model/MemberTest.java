package com.bibliotheque.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    @DisplayName("les attributs du membre sont bien restitues")
    void attributsRestitues() {
        Member membre = new Member("m1", "Ada Lovelace", "ada@example.com");

        assertEquals("m1", membre.getId());
        assertEquals("Ada Lovelace", membre.getName());
        assertEquals("ada@example.com", membre.getEmail());
    }
}
