package vichungbach.com.example.shopgaminggear.model;

import java.util.List;

public class productModel {
    boolean success;
    String  message;

    List<products> result;

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

    public List<products> getResult() {
        return result;
    }

    public void setResult(List<products> result) {
        this.result = result;
    }
}
