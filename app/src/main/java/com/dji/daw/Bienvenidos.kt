package com.dji.daw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen

class Bienvenidos : AppCompatActivityFullScreen(), View.OnClickListener {

    protected var avolarBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenidos)
        iniciarUI()

    }

    private fun iniciarUI() {
        avolarBtn = findViewById<View>(R.id.buttonVamosAVolar) as Button
        avolarBtn!!.setOnClickListener(this)

    }
    override fun onClick(v:View) {
        when (v.id) {
            R.id.buttonVamosAVolar -> {
                Toast.makeText(this@Bienvenidos, "Pulse a volar.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Bienvenidos, Home::class.java)
                    startActivity(i) //iniciar nueva actividad
                    finish()
                }, 3000)

            }
            else ->
            {
                Toast.makeText(this@Bienvenidos, "Error.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}