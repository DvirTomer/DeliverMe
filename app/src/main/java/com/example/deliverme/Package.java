package com.example.deliverme;

public class Package {
    String citySrc;
    String streetSrc;
    String dateSrc;
    String timeSrc;
    String cityDst;
    String streetDst;
    String dateDst;
    String timeDst;
    String note;
    String product;
    String pacID;
    String userID;
    String status;
    String sender;
    public Package(String citySrc, String streetSrc, String dateSrc, String timeSrc, String cityDst, String streetDst, String dateDst, String timeDst, String note, String product, String pacID, String userID,String status,String sender) {
        this.citySrc = citySrc;
        this.streetSrc = streetSrc;
        this.dateSrc = dateSrc;
        this.timeSrc = timeSrc;
        this.note = note;
        this.cityDst = cityDst;
        this.streetDst = streetDst;
        this.dateDst = dateDst;
        this.timeDst = timeDst;
        this.product = product;
        this.pacID=pacID;
        this.userID=userID;
        this.status=status;
        this.sender=sender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCitySrc() {
        return citySrc;
    }

    public String getStreetSrc() {
        return streetSrc;
    }

    public String getDateSrc() {
        return dateSrc;
    }

    public String getTimeSrc() {
        return timeSrc;
    }

    public String getNote() {
        return note;
    }

    public String getCityDst() {
        return cityDst;
    }

    public String getStreetDst() {
        return streetDst;
    }

    public String getDateDst() {
        return dateDst;
    }

    public String getTimeDst() {
        return timeDst;
    }

    public String getProduct() {
        return product;
    }

    public void setCitySrc(String citySrc) {
        this.citySrc = citySrc;
    }

    public void setStreetSrc(String streetSrc) {
        this.streetSrc = streetSrc;
    }

    public void setDateSrc(String dateSrc) {
        this.dateSrc = dateSrc;
    }

    public void setPacID(String pacID) {
        this.pacID = pacID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPacID() {
        return pacID;
    }

    public String getUserID() {
        return userID;
    }

    public void setTimeSrc(String timeSrc) {
        this.timeSrc = timeSrc;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCityDst(String cityDst) {
        this.cityDst = cityDst;
    }

    public void setStreetDst(String streetDst) {
        this.streetDst = streetDst;
    }

    public void setDateDst(String dateDst) {
        this.dateDst = dateDst;
    }

    public void setTimeDst(String timeDst) {
        this.timeDst = timeDst;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
