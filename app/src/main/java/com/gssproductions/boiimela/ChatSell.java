package com.gssproductions.boiimela;

public class ChatSell {

    private String bookTitle, senderName, bookPrice;

    public ChatSell() {
    }

    public ChatSell(String bookTitle, String senderName, String bookPrice) {
        this.bookTitle = bookTitle;
        this.senderName = senderName;
        this.bookPrice = bookPrice;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }
}
