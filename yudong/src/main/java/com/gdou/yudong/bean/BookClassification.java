package com.gdou.yudong.bean;

public class BookClassification {
    private Integer bookClassificationId;

    private String bookClassificationName;

    public Integer getBookClassificationId() {
        return bookClassificationId;
    }

    public void setBookClassificationId(Integer bookClassificationId) {
        this.bookClassificationId = bookClassificationId;
    }

    public String getBookClassificationName() {
        return bookClassificationName;
    }

    public void setBookClassificationName(String bookClassificationName) {
        this.bookClassificationName = bookClassificationName == null ? null : bookClassificationName.trim();
    }
}