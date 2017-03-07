package hu.bme.aut.realmdemo;

import io.realm.RealmObject;

/**
 * Created by Peter on 2017. 03. 07..
 */

public class Subject extends RealmObject {
    private String title;
    private String code;
    private int year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

