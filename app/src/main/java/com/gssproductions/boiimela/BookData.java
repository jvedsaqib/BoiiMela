package com.gssproductions.boiimela;

public class BookData {

    String address, authorName, description,
            imgUrl0, imgUrl1, imgUrl2,
            phoneNumber, price, publisherName,
            coverType,
            title, uid, seller_name;

    public String getAddress() {
        return address;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl0() {
        return imgUrl0;
    }

    public String getImgUrl1() {
        return imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPrice() {
        return price;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getTitle() {
        return title;
    }

    public String getUid() {
        return uid;
    }

    public String getCoverType() {
        return coverType;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }
}
