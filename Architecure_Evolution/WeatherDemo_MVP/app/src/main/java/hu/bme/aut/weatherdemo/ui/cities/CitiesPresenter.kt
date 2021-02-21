package hu.bme.aut.weatherdemo.ui.cities

import android.content.Context
import hu.bme.aut.weatherdemo.events.RxBusEvent
import hu.bme.aut.weatherdemo.events.EventBus
import hu.bme.aut.weatherdemo.repository.database.AppDatabase
import hu.bme.aut.weatherdemo.ui.Presenter
import hu.bme.aut.weatherdemo.ui.cities.adapter.CityAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_scrolling.*


object CitiesPresenter : Presenter<CitiesScreen?>() {

    override fun attachScreen(screen: CitiesScreen?) {
        super.attachScreen(screen)

        EventBus.listen(RxBusEvent.CitiesEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                screen?.showCities(it.citiesList)
            }
    }


    override fun detachScreen() {
        super.detachScreen()
    }


    fun queryCities(context: Context) {
        Thread {
            var citiesList = AppDatabase.getInstance(context).cityDao().getAllCities()
            EventBus.publish(RxBusEvent.CitiesEvent(citiesList))
        }.start()
    }

}
