package hu.bme.aut.weatherdemo.events

import hu.bme.aut.weatherdemo.model.City

class RxBusEvent {
    data class CitiesEvent(val citiesList: List<City>)
}

