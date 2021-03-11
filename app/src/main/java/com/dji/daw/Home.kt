package com.dji.daw

import android.content.Intent
import android.os.*
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class Home : AppCompatActivityFullScreen(),View.OnClickListener {

    protected var profileBtn: Button? = null
    protected var configuracionBtn: Button? = null
    protected var multimediaBtn: Button? = null
    protected var eventosBtn: Button? = null
    protected var volarBtn: FloatingActionButton? = null
    protected var testBtn: FloatingActionButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        iniciarUI()

    }

    private fun iniciarUI() {
        profileBtn = findViewById<View>(R.id.buttonProfile) as Button
        profileBtn!!.setOnClickListener(this)

        configuracionBtn = findViewById<View>(R.id.buttonConfiguracion) as Button
        configuracionBtn!!.setOnClickListener(this)

        multimediaBtn = findViewById<View>(R.id.buttonMultimedia) as Button
        multimediaBtn!!.setOnClickListener(this)

        eventosBtn = findViewById<View>(R.id.buttonEventos) as Button
        eventosBtn!!.setOnClickListener(this)

        volarBtn = findViewById<View>(R.id.buttonVolarAhora) as FloatingActionButton
        volarBtn!!.setOnClickListener(this)

        testBtn = findViewById<View>(R.id.floatingActionButtonAdmin) as FloatingActionButton
        testBtn!!.setOnClickListener(this)

    }
    override fun onClick(v:View) {
        when (v.id) {
            R.id.buttonProfile -> {
                Toast.makeText(this@Home, "Pulse Profile.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Home, Profile::class.java)
                    startActivity(i) //iniciar nueva actividad
                    //finish()
                }, 3000)

            }

            R.id.buttonConfiguracion -> {
                Toast.makeText(this@Home, "Pulse Configuracion.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Home, Configuracion::class.java)
                    startActivity(i) //iniciar nueva actividad
                    //finish()
                }, 3000)

            }

            R.id.buttonMultimedia -> {
                Toast.makeText(this@Home, "Pulse Multimedia.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Home, Multimedia::class.java)
                    startActivity(i) //iniciar nueva actividad
                    //finish()
                }, 3000)

            }

            R.id.buttonEventos -> {
                Toast.makeText(this@Home, "Pulse Eventos.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Home, Eventos::class.java)
                    startActivity(i) //iniciar nueva actividad
                    //finish()
                }, 3000)

            }
            R.id.buttonVolarAhora -> {
                Toast.makeText(this@Home, "Pulse Volar.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Home, WidgetVueloFPV::class.java)
                    startActivity(i) //iniciar nueva actividad
                }, 3000)

            }
            R.id.floatingActionButtonAdmin -> {
                Toast.makeText(this@Home, "Pulse Volar.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Home, Configuracion::class.java)
                    startActivity(i) //iniciar nueva actividad
                }, 3000)

            }

            else ->
            {
                Toast.makeText(this@Home, "Error.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}





