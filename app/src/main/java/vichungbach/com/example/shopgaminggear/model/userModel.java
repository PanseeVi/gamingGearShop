package vichungbach.com.example.shopgaminggear.model;

import java.util.List;

public class userModel {
    boolean success;
    String message;
    List<users> result;

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

    public List<users> getResult() {
        return result;
    }

    public void setResult(List<users> result) {
        this.result = result;
    }
}
