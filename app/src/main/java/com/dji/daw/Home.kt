package com.dji.daw

import android.content.Intent
import android.os.*
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        hideSystemUI()

        val fab = findViewById<FloatingActionButton>(R.id.BotonVolarAhora)
        fab.setOnClickListener {
            Toast.makeText(this@Home, "A volar", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Home, TestComponent::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }


        val buttonProfile = findViewById<Button>(R.id.buttonProfile)
        buttonProfile.setOnClickListener {

            Toast.makeText(this@Home, "Pulse profile.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Home, Profile::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

        val buttonConfiguracion = findViewById<Button>(R.id.buttonConfiguracion)
        buttonConfiguracion.setOnClickListener {

            Toast.makeText(this@Home, "Pulse configuracion.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Home, Configuracion::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

        val buttonMultimedia = findViewById<Button>(R.id.buttonMultimedia)
        buttonMultimedia.setOnClickListener {

            Toast.makeText(this@Home, "Pulse multimedia.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Home, Multimedia::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

        val buttonEventos = findViewById<Button>(R.id.buttonEventos)
        buttonEventos.setOnClickListener {

            Toast.makeText(this@Home, "Pulse eventos.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Home, Eventos::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}





