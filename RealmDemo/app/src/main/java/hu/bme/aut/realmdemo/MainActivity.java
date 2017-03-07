package hu.bme.aut.realmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Subject sbj = new Subject();
        sbj.setTitle("BSZ");
        sbj.setCode("VIK01");
        sbj.setYear(1);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(config);

        /*final RealmResults<Subject> subjects = realm.where(Subject.class).lessThan("year",
                2).findAll();
        //subjects.size();

        realm.beginTransaction();
        final Subject newSbj = realm.copyToRealm(sbj);
        Student student = realm.createObject(Student.class);
        student.getSubjects().add(newSbj);
        realm.commitTransaction();*/

        RealmQuery<Student> query = realm.where(Student.class);
        RealmResults<Student> result1 = query.findAll();

        for (Student u : result1) {
            Toast.makeText(this, ""+u.getSubjects().size(), Toast.LENGTH_SHORT).show();
        }
    }
}
