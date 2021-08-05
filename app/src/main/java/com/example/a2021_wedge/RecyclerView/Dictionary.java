package com.example.a2021_wedge.RecyclerView;

public class Dictionary {

    private String table_person;
    private String table_amount;

    public String getTable_person() {
        return table_person;
    }

    public void setTable_person(String table_person) {
        this.table_person = table_person;
    }

    public String getTable_amount() {
        return table_amount;
    }

    public void setTable_amount(String table_amount) {
        this.table_amount = table_amount;
    }

    public Dictionary(String table_person, String table_amount) {
        this.table_person = table_person;
        this.table_amount = table_amount;
    }

}
