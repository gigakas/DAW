package com.dji.daw

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen
import com.dji.daw.db.VolleySolicitudes
import java.util.regex.Pattern

class RegistroUsuario : AppCompatActivityFullScreen(), View.OnClickListener {

    protected var registrarUsuarioBtn: Button? = null
    protected var regresarBtn :Button? = null
    protected var nombresTextInput: TextView? = null
    protected var apellidosTextInput: TextView? = null
    protected var correoTextInput: TextView? = null
    protected var passwordTextInput: TextView? = null
    protected var passwordConfirTextInput: TextView? = null

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

        nombresTextInput = findViewById<View>(R.id.editTextTextNombres) as? TextView
        apellidosTextInput = findViewById<View>(R.id.editTextTextApellidos) as? TextView
        correoTextInput = findViewById<View>(R.id.editTextTextEmail) as? TextView
        passwordTextInput = findViewById<View>(R.id.editTextPasswordRegistro) as? TextView
        passwordConfirTextInput = findViewById<View>(R.id.editTextTextPasswordConfirmRegistro) as? TextView

    }

    override fun onClick(v: View) {
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

                val emailValidacion=  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z0-9.]+"
                val nombres = nombresTextInput?.text.toString()
                val apellidos = apellidosTextInput?.text.toString()
                val correo = correoTextInput?.text.toString()
                val password = passwordTextInput?.text.toString()
                val passwordVal = passwordConfirTextInput?.text.toString()

                if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || password.isEmpty() ||  passwordVal.isEmpty()){

                    Toast.makeText(this@RegistroUsuario,"Por favor llenar todos los campos",Toast.LENGTH_SHORT).show()

                } else if(password != passwordVal){

                    Toast.makeText(this@RegistroUsuario,"Los campos de contraseña deben ser iguales",Toast.LENGTH_SHORT).show()

                }else if( correo.trim().matches(Regex(emailValidacion))) {

                    VolleySolicitudes().regUsuarioNuevo(this@RegistroUsuario, nombres, apellidos, correo, password)
                    nombresTextInput?.setText("")
                    apellidosTextInput?.setText("")
                    correoTextInput?.setText("")
                    passwordTextInput?.setText("")
                    passwordConfirTextInput?.setText("")

                } else {

                    Toast.makeText(this@RegistroUsuario,"Ingrese un correo valido", Toast.LENGTH_SHORT).show()
                    correoTextInput?.setText("")
                    correoTextInput?.requestFocus()
                                                              
                }


            }else -> {

                Toast.makeText(this@RegistroUsuario, "Error.", Toast.LENGTH_SHORT).show()
        }
        }
    }


}

