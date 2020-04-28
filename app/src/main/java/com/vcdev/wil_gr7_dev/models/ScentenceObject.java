package com.vcdev.wil_gr7_dev.models;

public class ScentenceObject {
    String scentence, jumbledScentence;

    public ScentenceObject(String scentence, String jumbledScentence) {
        this.scentence = scentence;
        this.jumbledScentence = jumbledScentence;
    }

    @Override
    public String toString() {
        return "ScentenceObject{" +
                "scentence='" + scentence + '\'' +
                ", jumbledScentence='" + jumbledScentence + '\'' +
                '}';
    }

    public String getScentence() {
        return scentence;
    }

    public void setScentence(String scentence) {
        this.scentence = scentence;
    }

    public String getJumbledScentence() {
        return jumbledScentence;
    }

    public void setJumbledScentence(String jumbledScentence) {
        this.jumbledScentence = jumbledScentence;
    }
}
