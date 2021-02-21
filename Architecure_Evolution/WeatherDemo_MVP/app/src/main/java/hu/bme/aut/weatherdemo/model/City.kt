package hu.bme.aut.weatherdemo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City (
    @PrimaryKey(autoGenerate = true) var cityId : Long?,
    @ColumnInfo(name = "cityname") var cityname : String
)