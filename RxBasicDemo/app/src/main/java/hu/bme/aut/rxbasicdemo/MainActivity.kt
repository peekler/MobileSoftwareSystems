package hu.bme.aut.rxbasicdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var stringDisposable: Disposable? = null
    private lateinit var stringObservable: Observable<String>

    private lateinit var seekBarObservable: Observable<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            //startStringObserver()

            Observable.defer {
                listOf("This is the first text!",
                    "This is the second text!",
                    "This is the third text!")
                    .toObservable()
            }
                .filter { it.contains("first") || it.contains("second") }
                .map { it.toUpperCase() }
                .subscribeBy(
                    onNext = { Log.d("Text", "Text changed:$it") },
                    onError = { Log.d("Text", "Error:${it.message}") },
                    onComplete = { Log.d("Text", "Finished") }
                )

        }
    }

    private fun startStringObserver() {
        stringObservable = ObservableFactory.buildStringObservable()
        seekBarObservable = ObservableFactory.buildObservableFromSeekBar(seekBar)

        stringDisposable = Observable.combineLatest(stringObservable,
            seekBarObservable, ::TextWithColor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::onStringWithColorReceived,
                    ::onError, ::onFinished)
    }

    override fun onDestroy() {
        stringDisposable?.dispose()
        super.onDestroy()
    }


    private fun onStringWithColorReceived(textWithColor: TextWithColor) {
        demoTextView.visibility = View.VISIBLE
        demoTextView.setTextColor(textWithColor.color)
        demoTextView.text = textWithColor.text
    }

    private fun onFinished() {}

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }


}