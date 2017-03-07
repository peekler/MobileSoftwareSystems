package hu.aut.daggerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import hu.aut.daggerdemo.network.NetworkCall;
import hu.aut.daggerdemo.network.NetworkModule;

public class MainActivity extends AppCompatActivity {
    @Inject
    NetworkCall networkCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerDemoApplication.injector.inject(this);
        Toast.makeText(this, "result: "+networkCall.add(1, 3), Toast.LENGTH_LONG).show();
    }
}