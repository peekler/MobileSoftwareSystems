package hu.bme.aut.rxbasicdemo

import android.graphics.Color
import android.widget.SeekBar
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object ObservableFactory {

    fun buildStringObservable(): Observable<String> = Observable.defer {
        Observable.interval(2, TimeUnit.SECONDS, Schedulers.computation()).map { ms ->
            when (ms.toInt() % 8) {
                0 -> "KitKat"
                1 -> "Lollipop"
                2 -> "Marshmallow"
                3 -> "Nougat"
                4 -> "Oreo"
                5 -> "Pie"
                6 -> "10"
                7 -> "11"
                else -> ""
            }
        }
    }

    fun buildObservableFromSeekBar(seekBar: SeekBar): Observable<Int> {
        return Observable.create { subscriber ->

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val color = Color.HSVToColor(floatArrayOf(progress.toFloat() / 100.0f * 360.0f, 0.6f, 0.6f))
                    subscriber.onNext(color)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }
}