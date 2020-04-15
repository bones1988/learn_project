package com.epam.esm.certificate;

public class WrongCertificate extends GiftCertificate {
    private String wrong;
    private int error;

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
