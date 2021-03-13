package com.dji.daw

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen

class Profile : AppCompatActivityFullScreen(),View.OnClickListener {

    protected var btnRegresarProfile: Button? = null
    protected var labelTextNombesFull: TextView? = null
    protected var labelTextCorreo: TextView? = null
    protected var labelTextTokenID: TextView? = null
    protected var labelTextTipoUsuario: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        iniciarUI()

    }

    @SuppressLint("SetTextI18n")
    private fun iniciarUI() {
        btnRegresarProfile = findViewById<Button>(R.id.buttonRegresarProfile) as Button
        btnRegresarProfile!!.setOnClickListener(this)

        labelTextNombesFull = findViewById<TextView>(R.id.textViewNombreFullProfile) as TextView
        labelTextNombesFull!!.text = "Nombre: "
        labelTextNombesFull!!.append(usuarioOnlineNombresComletos)

        labelTextCorreo = findViewById<TextView>(R.id.textViewCorreoProfile) as TextView
        labelTextCorreo!!.text = "Correo: "
        labelTextCorreo!!.append(usuarioOnlineCorreo)

        labelTextTipoUsuario = findViewById<TextView>(R.id.textViewTipoUsuarioProfile) as TextView
        labelTextTipoUsuario!!.text = "Tipo Usuario: "
        labelTextTipoUsuario!!.append(usuarioOnlineTipoUsuario)

        labelTextTokenID = findViewById<TextView>(R.id.textViewTokenLoginProfile) as TextView
        labelTextTokenID!!.text = "Token Login: "
        labelTextTokenID!!.append(usuarioOnlineTokenID)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonRegresarProfile -> {
                Toast.makeText(this@Profile, "Pulse regresar.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent( this@Profile, Home::class.java)
                    startActivity(i) //iniciar nueva actividad
                    finish()
                }, 3000)
            }

            else -> {
                Toast.makeText(this@Profile, "Error.", Toast.LENGTH_SHORT).show()
            }
        }
    }



}