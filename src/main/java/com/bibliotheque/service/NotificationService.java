package com.bibliotheque.service;

public class NotificationService {

    public String notifyLoan(String memberName, String bookTitle) {
        return "Bonjour " + memberName + ", vous avez emprunte \"" + bookTitle + "\".";
    }

    public String notifyReturn(String memberName, String bookTitle) {
        return "Bonjour " + memberName + ", merci d'avoir rendu \"" + bookTitle + "\".";
    }
}
