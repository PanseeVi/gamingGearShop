package vichungbach.com.example.shopgaminggear.model;

import java.util.List;

public class pdTDModel {
    boolean success;
    String  message;

    List<productTD> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<productTD> getResult() {
        return result;
    }

    public void setResult(List<productTD> result) {
        this.result = result;
    }
}
