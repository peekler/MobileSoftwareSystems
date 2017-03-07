package hu.aut.reflectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = (TextView) findViewById(R.id.tvData);

        showReflect();
    }

    private void showReflect() {
        Class classToUse  = null;
        try {
            classToUse = Class.forName("android.app.NotificationManager");
            Field[] classFields = classToUse.getDeclaredFields();
            tvData.setText("");
            for(Field f : classFields){
                tvData.append(f.getName()+"\n");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
