package com.example.deliverme;

import android.widget.EditText;
import android.widget.Spinner;

public class Package {
    String citySrc;
    String streetSrc;
    String timeSrc1;
    String timeSrc2;
    String note;
    String cityDst;
    String streetDst;
    String timeDst1;
    String timeDst2;
    String product;

    public Package(String citySrc, String streetSrc, String timeSrc1, String timeSrc2, String note, String cityDst, String streetDst, String timeDst1, String timeDst2, String product) {
        this.citySrc = citySrc;
        this.streetSrc = streetSrc;
        this.timeSrc1 = timeSrc1;
        this.timeSrc2 = timeSrc2;
        this.note = note;
        this.cityDst = cityDst;
        this.streetDst = streetDst;
        this.timeDst1 = timeDst1;
        this.timeDst2 = timeDst2;
        this.product = product;
    }

    public String getCitySrc() {
        return citySrc;
    }

    public String getStreetSrc() {
        return streetSrc;
    }

    public String getTimeSrc1() {
        return timeSrc1;
    }

    public String getTimeSrc2() {
        return timeSrc2;
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

    public String getTimeDst1() {
        return timeDst1;
    }

    public String getTimeDst2() {
        return timeDst2;
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

    public void setTimeSrc1(String timeSrc1) {
        this.timeSrc1 = timeSrc1;
    }

    public void setTimeSrc2(String timeSrc2) {
        this.timeSrc2 = timeSrc2;
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

    public void setTimeDst1(String timeDst1) {
        this.timeDst1 = timeDst1;
    }

    public void setTimeDst2(String timeDst2) {
        this.timeDst2 = timeDst2;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
