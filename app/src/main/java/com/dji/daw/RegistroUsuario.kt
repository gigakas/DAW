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

class RegistroUsuario : AppCompatActivityFullScreen(), View.OnClickListener {

    protected var registrarUsuarioBtn: Button? = null
    protected var regresarBtn :Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)
        iniciarUI()
    }

    private fun iniciarUI() {
        registrarUsuarioBtn = findViewById<View>(R.id.buttonRegistroNuevoUsuario) as Button
        registrarUsuarioBtn!!.setOnClickListener(this)

        regresarBtn = findViewById<View>(R.id.buttonRegresar) as Button
        regresarBtn!!.setOnClickListener(this)

    }

    override fun onClick(v:View) {
        when (v.id) {
            R.id.buttonRegresar -> {
                Toast.makeText(this@RegistroUsuario, "Pulse Regresar.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@RegistroUsuario, Login::class.java)
                    startActivity(i) //iniciar nueva actividad
                    finish()
                }, 3000)

            }
            R.id.buttonRegistroNuevoUsuario -> {

                Toast.makeText(this@RegistroUsuario, "Pulse registro.", Toast.LENGTH_SHORT).show()
                /*Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@RegistroUsuario, RegistroUsuario::class.java)
                    startActivity(i) //start new activity
                    finish()
                }, 3000)*/

            }else -> {

                Toast.makeText(this@RegistroUsuario, "Error.", Toast.LENGTH_SHORT).show()
        }
        }
    }


}