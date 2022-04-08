package com.maksimcom.a8ballexample

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.GRAVITY_EARTH
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.lang.Math.sqrt
import java.util.*

class MainActivity : AppCompatActivity() {
    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager)!!.registerListener(sensorListener, sensorManager!!
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        acceleration = 10f
        currentAcceleration = GRAVITY_EARTH
        lastAcceleration = GRAVITY_EARTH

        val askMeBtn : Button = findViewById(R.id.button)
        askMeBtn.setOnClickListener { shakeBall() }
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta
            if (acceleration > 12) {
                Toast.makeText(applicationContext, "Shake event detected", Toast.LENGTH_SHORT).show()
                val ballTextView: TextView = findViewById(R.id.TextView)
                val textResource = when (Random().nextInt(7)) {
                    1 -> "Жизнь боль"
                    2 -> "Не стоит"
                    3 -> "Лучше подумай"
                    4 -> "Да"
                    5 -> "Нет"
                    6 -> "Скорее нет"
                    else -> {"Скорее да"}
                }
                ballTextView.setText(textResource)
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }

    //оставил на всякий случай, да, не оптимизировал, извините
    private fun shakeBall() {
        val ballTextView: TextView = findViewById(R.id.TextView)
        val textResource = when (Random().nextInt(7)) {
            1 -> "Жизнь боль"
            2 -> "Не стоит"
            3 -> "Лучше подумай"
            4 -> "Да"
            5 -> "Нет"
            6 -> "Скорее нет"
            else -> {"Скорее да"}
        }
        ballTextView.setText(textResource)
    }
}