package com.dji.daw

import android.content.Intent
import android.os.*
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dji.daw.controles.AppCompatActivityFullScreen
import com.dji.daw.controles.DJIControl
import com.dji.daw.controles.PermisosApp
import org.json.JSONException
import java.sql.Connection
import java.util.*


class Login : AppCompatActivityFullScreen(), View.OnClickListener {

    protected var loginBtn: Button? = null
    protected var registroUsuarioBtn :Button? = null
    protected var recuperarPassBtn :Button? = null

    internal var conexion: Connection? = null
    internal var username = "daw" //usuario
    internal var password = "Drone810210*" // pass
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        iniciarUI()

        PermisosApp(this@Login).validarPermisos()
        DJIControl(this@Login).startSDKRegistration()
        requestQueue = Volley.newRequestQueue(this)

    }


    private fun iniciarUI() {
        loginBtn = findViewById<View>(R.id.buttonLogin) as Button
        loginBtn!!.setOnClickListener(this)

        registroUsuarioBtn = findViewById<View>(R.id.buttonRegistroNuevoUsuario) as Button
        registroUsuarioBtn!!.setOnClickListener(this)

        recuperarPassBtn = findViewById<View>(R.id.buttonRecuperarPassword) as Button
        recuperarPassBtn!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonLogin -> {

                jsonParse()
                //Toast.makeText(this@Login, "Pulse login.", Toast.LENGTH_SHORT).show()


                 Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Login, Bienvenidos::class.java)
                    startActivity(i) //iniciar nueva actividad
                    finish()
                }, 3000)

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


    private fun jsonParse() {
        val url = "https://www.albanss.com/loginUser.login"
        val request = JsonObjectRequest(Request.Method.POST, url, null, { response ->
            try {
                val mensaje = response.getString("mensaje")
                Toast.makeText(this@Login, "$mensaje", Toast.LENGTH_SHORT).show()
                /*val jsonArray = response.getJSONArray("respuesta")
                for (i in 0 until jsonArray.length()) {

                    val error = jsonArray.getJSONObject(i)
                    val mensaje = jsonArray.getJSONObject(i)

                    //textView.append("$firstName, $age, $mail\n\n")
                    //
                }*/
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        },
                { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }




}