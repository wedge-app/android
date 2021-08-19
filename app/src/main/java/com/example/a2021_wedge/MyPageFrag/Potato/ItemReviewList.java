package com.example.a2021_wedge.MyPageFrag.Potato;

public class ItemReviewList {
    private String store_name;
    private String rate;
    private String review;
    private String list;

    public ItemReviewList(String store_name, String rate, String review, String list) {
        this.store_name = store_name;
        this.rate = rate;
        this.review = review;
        this.list = list;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}

