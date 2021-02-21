package hu.bme.aut.weatherdemo.repository.database

import androidx.room.*
import hu.bme.aut.weatherdemo.model.City

@Dao
interface CityDAO {
    @Query("SELECT * FROM cities")
    fun getAllCities(): List<City>

    @Insert
    fun insertCity(city: City) : Long

    @Delete
    fun deleteCity(city: City)

    @Update
    fun updateCity(city: City)
}