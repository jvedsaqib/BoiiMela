package com.gssproductions.boiimela;

public class ReadwriteUserDetails {

    public String name,address,doB,gender,mobile,password;
    //constructor
    public ReadwriteUserDetails(String textFullName, String textAddress, String textDob, String textGender, String textMobile, String textPwd){
        this.name=textFullName;
        this.address=textAddress;
        this.doB=textDob;
        this.gender=textGender;
        this.mobile=textMobile;
        this.password=textPwd;
    }

}
