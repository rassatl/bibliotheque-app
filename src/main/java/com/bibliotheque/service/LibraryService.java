package com.bibliotheque.service;

import com.bibliotheque.model.Book;
import com.bibliotheque.model.Loan;
import com.bibliotheque.model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LibraryService {

    private final Map<String, Book> books = new LinkedHashMap<>();
    private final Map<String, Member> members = new LinkedHashMap<>();
    private final List<Loan> loans = new ArrayList<>();
    private final NotificationService notificationService;

    public LibraryService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void removeBook(String bookId) {
        books.remove(bookId);
    }

    public Optional<Book> findBookById(String bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    public List<Book> searchByTitle(String keyword) {
        String needle = keyword.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(needle)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByAuthor(String keyword) {
        String needle = keyword.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(needle)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> listAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public String borrowBook(String bookId, String memberId) {
        Book book = books.get(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Livre inconnu : " + bookId);
        }
        Member member = members.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Membre inconnu : " + memberId);
        }
        if (!book.isAvailable()) {
            throw new IllegalStateException("Livre deja emprunte : " + bookId);
        }

        book.setAvailable(false);
        loans.add(new Loan(bookId, memberId, LocalDate.now()));
        return notificationService.notifyLoan(member.getName(), book.getTitle());
    }

    public String returnBook(String bookId) {
        Book book = books.get(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Livre inconnu : " + bookId);
        }

        Loan activeLoan = null;
        for (Loan loan : loans) {
            if (loan.getBookId().equals(bookId) && !loan.isReturned()) {
                activeLoan = loan;
            }
        }
        if (activeLoan == null) {
            throw new IllegalStateException("Aucun emprunt en cours pour : " + bookId);
        }

        activeLoan.markReturned(LocalDate.now());
        book.setAvailable(true);
        Member member = members.get(activeLoan.getMemberId());
        String memberName = member != null ? member.getName() : "membre";
        return notificationService.notifyReturn(memberName, book.getTitle());
    }
}
