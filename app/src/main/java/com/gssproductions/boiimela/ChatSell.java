package com.gssproductions.boiimela;

public class ChatSell {

    private String bookTitle, senderName, bookPrice, sellerUID, buyerUID;

    public ChatSell() {
    }

    public ChatSell(String bookTitle, String senderName, String bookPrice, String sellerUID, String buyerUID) {
        this.bookTitle = bookTitle;
        this.senderName = senderName;
        this.bookPrice = bookPrice;
        this.sellerUID = sellerUID;
        this.buyerUID = buyerUID;
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

    public String getSellerUID() {
        return sellerUID;
    }

    public void setSellerUID(String sellerUID) {
        this.sellerUID = sellerUID;
    }

    public String getBuyerUID() {
        return buyerUID;
    }

    public void setBuyerUID(String buyerUID) {
        this.buyerUID = buyerUID;
    }
}
