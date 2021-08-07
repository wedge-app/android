package com.example.a2021_wedge.Sajang;

public class Person {
    int image;
    String name;
    int number;

    public Person(int image, String name, int number) {
        this.image = image;
        this.name = name;
        this.number = number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
