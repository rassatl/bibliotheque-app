package com.bibliotheque.model;

import java.time.LocalDate;

public class Loan {

    private String bookId;
    private String memberId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(String bookId, String memberId, LocalDate loanDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.loanDate = loanDate;
    }

    public String getBookId()       { return bookId; }
    public String getMemberId()     { return memberId; }
    public LocalDate getLoanDate()  { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned()     { return returnDate != null; }

    public void markReturned(LocalDate date) {
        this.returnDate = date;
    }
}
