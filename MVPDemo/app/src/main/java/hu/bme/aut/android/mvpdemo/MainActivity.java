package hu.bme.aut.android.mvpdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hu.bme.aut.android.mvpdemo.main.MainPresenter;
import hu.bme.aut.android.mvpdemo.main.MainScreen;

public class MainActivity extends AppCompatActivity implements MainScreen {

    private TextView tvData;

    private MainPresenter mainPresenter = new MainPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData = findViewById(R.id.tvData);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.doCalculcation(1, 2);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.detachScreen();
    }

    @Override
    public void showCalcResult(int result) {
        tvData.setText(String.valueOf(result));
    }
}
