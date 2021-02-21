package hu.bme.aut.android.mvpdemo.main;

public class MainPresenter {

    MainScreen mainScreen;

    public void attachScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    public void detachScreen() {
        this.mainScreen = null;
    }

    public void doCalculcation(int a, int b) {
        if (mainScreen != null) {
            mainScreen.showCalcResult(a + b);
        }
    }

}
