package hu.bme.aut.realmdemo;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Peter on 2017. 03. 07..
 */

public class Student extends RealmObject {
    private String name;
    private RealmList<Subject> subjects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(RealmList<Subject> subjects) {
        this.subjects = subjects;
    }
}

