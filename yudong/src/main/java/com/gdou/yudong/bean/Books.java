package com.gdou.yudong.bean;

import java.io.Serializable;
import java.util.Date;

public class Books implements Serializable{

    private Integer bookId;

    private String bookName;

    private Integer bookClassificationId;

    private String bookIntroduction;

    private String bookAuthor;

    private Float bookSize;

    private Double bookPrice;

    private String bookCoverPath;

    private String bookLocation;

    private String uploadPerson;

    private Date uploadTime;

    private Integer bookState;

    private Integer bookDownloads;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public Integer getBookClassificationId() {
        return bookClassificationId;
    }

    public void setBookClassificationId(Integer bookClassificationId) {
        this.bookClassificationId = bookClassificationId;
    }

    public String getBookIntroduction() {
        return bookIntroduction;
    }

    public void setBookIntroduction(String bookIntroduction) {
        this.bookIntroduction = bookIntroduction == null ? null : bookIntroduction.trim();
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor == null ? null : bookAuthor.trim();
    }

    public Float getBookSize() {
        return bookSize;
    }

    public void setBookSize(Float bookSize) {
        this.bookSize = bookSize;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookCoverPath() {
        return bookCoverPath;
    }

    public void setBookCoverPath(String bookCoverPath) {
        this.bookCoverPath = bookCoverPath == null ? null : bookCoverPath.trim();
    }

    public String getBookLocation() {
        return bookLocation;
    }

    public void setBookLocation(String bookLocation) {
        this.bookLocation = bookLocation == null ? null : bookLocation.trim();
    }

    public String getUploadPerson() {
        return uploadPerson;
    }

    public void setUploadPerson(String uploadPerson) {
        this.uploadPerson = uploadPerson == null ? null : uploadPerson.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getBookState() {
        return bookState;
    }

    public void setBookState(Integer bookState) {
        this.bookState = bookState;
    }

    public Integer getBookDownloads() {
        return bookDownloads;
    }

    public void setBookDownloads(Integer bookDownloads) {
        this.bookDownloads = bookDownloads;
    }
}