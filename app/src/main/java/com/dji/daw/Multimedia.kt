package com.dji.daw

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen

open class Multimedia : AppCompatActivityFullScreen(), View.OnClickListener {

    private var btnRegresarMultimedia: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multimedia)
        iniciarUI()

    }

    private fun iniciarUI() {
        btnRegresarMultimedia = findViewById<Button>(R.id.buttonRegresarMultimedia)
        btnRegresarMultimedia!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
                R.id.buttonRegresarMultimedia -> {
                    Toast.makeText(this@Multimedia, "Pulse regresar.", Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        val i = Intent(this@Multimedia, Home::class.java)
                        startActivity(i) //iniciar nueva actividad
                        finish()
                    }, 3000)

                }

                R.id.buttonConfiguracion -> {
                    Toast.makeText(this@Multimedia, "Pulse Configuracion.", Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        val i = Intent(this@Multimedia, Home::class.java)
                        startActivity(i) //iniciar nueva actividad
                        //finish()
                    }, 3000)

                }

                else -> {
                    Toast.makeText(this@Multimedia, "Error.", Toast.LENGTH_SHORT).show()
                }
            }
    }


}