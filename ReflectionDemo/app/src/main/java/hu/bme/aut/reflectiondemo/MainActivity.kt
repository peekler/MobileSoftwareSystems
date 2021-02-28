package hu.bme.aut.reflectiondemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Field


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showReflect()
    }

    private fun showReflect() {
        var classToUse: Class<*>? = null
        try {
            classToUse = Class.forName("android.app.NotificationManager")
            val classFields: Array<Field> = classToUse.declaredFields
            tvData.text = ""
            for (f in classFields) {
                tvData!!.append(f.getName().toString() + "\n")
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
}

