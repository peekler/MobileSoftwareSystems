package hu.autsoft.demo.rxjava

import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.autsoft.demo.rxjava.model.TextWithColor
import hu.autsoft.demo.rxjava.observables.ObservableFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var stringDisposable: Disposable? = null
    private var networkDisposable: Disposable? = null

    private lateinit var stringObservable: Observable<String>
    private lateinit var seekBarObservable: Observable<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            //startStringObservable()
            startTimeObservable()
        }
    }

    private fun startStringObservable() {
        stringObservable = ObservableFactory.buildStringObservable()
        seekBarObservable = ObservableFactory.buildObservableFromSeekBar(seekBar)

        stringDisposable = Observable.combineLatest(stringObservable, seekBarObservable, ::TextWithColor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::onStringWithColorReceived, ::onError, ::onFinished)
    }

    private fun startTimeObservable() {
        networkDisposable = ObservableFactory.buildNetworkObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                        },
                        { onError(it) }
                )
    }

    override fun onDestroy() {
        stringDisposable?.dispose()
        networkDisposable?.dispose()
        super.onDestroy()
    }

    private fun onStringWithColorReceived(textWithColor: TextWithColor) {
        demoTextView.visibility = VISIBLE
        demoTextView.setTextColor(textWithColor.color)
        demoTextView.text = textWithColor.text
    }

    private fun onFinished() {}

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }


}