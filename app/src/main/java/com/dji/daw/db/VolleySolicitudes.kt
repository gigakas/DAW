package com.dji.daw.db

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dji.daw.controles.AppCompatActivityFullScreen.Companion.usuarioOnlineCorreo
import com.dji.daw.controles.AppCompatActivityFullScreen.Companion.usuarioOnlineNombresComletos
import com.dji.daw.controles.AppCompatActivityFullScreen.Companion.usuarioOnlineTipoUsuario
import com.dji.daw.controles.AppCompatActivityFullScreen.Companion.usuarioOnlineTokenID
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class VolleySolicitudes {

    //Login validacion
     fun loginUser(contextoActual: Context, contextSiguiente: Class<out Activity>?, correo: String, password: String) {

        val url = "https://www.albanss.com/loginUser.login"
        val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    //Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                    try {
                        //val respuestaAPI = response //full vector
                        val aprobado: Boolean = JSONObject(response).getBoolean("confirmado")
                        val mensaje: String? = JSONObject(response).getString("mensaje")

                        if (aprobado) {
                            usuarioOnlineTokenID = JSONObject(response).getString("tokenLogin")
                            usuarioOnlineNombresComletos = JSONObject(response).getString("nombreComleto")
                            usuarioOnlineCorreo = JSONObject(response).getString("correo")
                            usuarioOnlineTipoUsuario = JSONObject(response).getString("tipoUsuario")

                            Toast.makeText(contextoActual, mensaje, Toast.LENGTH_LONG).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                val i = Intent(contextoActual, contextSiguiente)
                                contextoActual.startActivity(i)
                                //(contextoActual.applicationContext as Activity).finish()

                            }, 2000)

                        } else {

                            Toast.makeText(contextoActual,mensaje, Toast.LENGTH_LONG).show()

                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(contextoActual, error.toString(), Toast.LENGTH_LONG).show()
                }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //parametros a enviar por post o get
                params["correo"] = correo
                params["password"] = password
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(contextoActual)
        requestQueue.add(stringRequest)
    }


    //registrar Usuario
    fun regUsuarioNuevo(contextoActual: Context, nombres: String, apellidos: String, correo:String,password:String) {

        val url = "https://www.albanss.com/registrar.usuario"
        val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    //Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                    try {
                        //val respuestaAPI = response.toString() //full vector

                        val respuesta: Boolean = JSONObject(response).getBoolean("confirmado")
                        val mensaje: String? = JSONObject(response).getString("mensaje")

                        if (respuesta) {

                            Toast.makeText(contextoActual, mensaje, Toast.LENGTH_LONG).show()

                        } else {

                            Toast.makeText(contextoActual, mensaje, Toast.LENGTH_LONG).show()

                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(contextoActual, error.toString(), Toast.LENGTH_LONG).show()
                }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //parametros a enviar por post o get
                params["nombres"] = nombres
                params["apellidos"] = apellidos
                params["correo"] = correo
                params["password"] = password
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(contextoActual)
        requestQueue.add(stringRequest)
    }


}