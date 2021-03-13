package com.dji.daw

import android.content.Intent
import android.os.*
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.dji.daw.controles.AppCompatActivityFullScreen
import com.dji.daw.controles.DJIControl
import com.dji.daw.controles.PermisosApp
import com.dji.daw.db.VolleySolicitudes
import java.util.*


class Login : AppCompatActivityFullScreen(), View.OnClickListener {

    protected var loginBtn: Button? = null
    protected var registroUsuarioBtn :Button? = null
    protected var recuperarPassBtn :Button? = null
    protected var correo:TextView? = null
    protected var password:TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        iniciarUI()
        PermisosApp(this@Login).validarPermisos()
        DJIControl(this@Login).startSDKRegistration()

    }


    private fun iniciarUI() {
        loginBtn = findViewById<View>(R.id.buttonLogin) as Button
        loginBtn!!.setOnClickListener(this)

        registroUsuarioBtn = findViewById<View>(R.id.buttonRegistroNuevoUsuario) as Button
        registroUsuarioBtn!!.setOnClickListener(this)

        recuperarPassBtn = findViewById<View>(R.id.buttonRecuperarPassword) as Button
        recuperarPassBtn!!.setOnClickListener(this)

        correo = findViewById<View>(R.id.editTextEmail) as TextView
        password = findViewById<View>(R.id.editTextPassword) as TextView

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonLogin -> {

                val emailValidacion=  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z0-9.]+"
                val usuario = correo?.text.toString()
                val password = password?.text.toString()

                if(usuario.isEmpty() || password.isEmpty()){

                    Toast.makeText(this@Login,"Por favor llenar todos los campos",Toast.LENGTH_SHORT).show()

                }else{

                    if( usuario.trim().matches(Regex(emailValidacion))){

                        VolleySolicitudes().loginUser(this@Login,Bienvenidos::class.java,usuario,password)

                    }else{

                        Toast.makeText(this@Login,"El correo no tiene un formato valido",Toast.LENGTH_SHORT).show()
                    }

                }



            }
            R.id.buttonRegistroNuevoUsuario -> {

                Toast.makeText(this@Login, "Pulse registro.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Login, RegistroUsuario::class.java)
                    startActivity(i) //start new activity
                    finish()
                }, 3000)
            }
            R.id.buttonRecuperarPassword -> {

                Toast.makeText(this@Login, "Pulse recover pass.", Toast.LENGTH_SHORT).show()

            }
            else -> {
                Toast.makeText(this@Login, "Error.", Toast.LENGTH_SHORT).show()
            }

        }
    }


}