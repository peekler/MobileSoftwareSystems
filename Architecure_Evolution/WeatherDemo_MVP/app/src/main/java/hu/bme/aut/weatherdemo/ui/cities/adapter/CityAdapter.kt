package hu.bme.aut.weatherdemo.ui.cities.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.weatherdemo.R
import hu.bme.aut.weatherdemo.ui.weather.WeatherDetailsActivity
import hu.bme.aut.weatherdemo.model.City
import kotlinx.android.synthetic.main.city_row.view.*

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder> {

    val context: Context
    var cityList = mutableListOf<City>()

    constructor(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.city_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cityList[position]
        holder.tvCityName.text = city.cityname

        holder.btnDelete.setOnClickListener {
            removeCity(holder.adapterPosition)
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(context, WeatherDetailsActivity::class.java)
            intent.putExtra("CITY_NAME", city.cityname)
            context.startActivity(intent)
        }
    }

    fun setCities(cities: List<City>) {
        this.cityList.clear()
        this.cityList.addAll(cities)
        notifyDataSetChanged()
    }

    fun addCity(city: City) {
        cityList.add(city)
        notifyItemInserted(cityList.lastIndex)
    }

    fun removeCity(position: Int) {
        cityList.removeAt(position)
        notifyItemRemoved(position)
    }


    inner class ViewHolder(cityView: View) : RecyclerView.ViewHolder(cityView) {
        val tvCityName = cityView.tvCityName
        val cardView = cityView.cardView
        val btnDelete = cityView.btnDelete
    }

}