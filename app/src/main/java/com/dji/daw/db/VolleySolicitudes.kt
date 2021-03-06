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
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class VolleySolicitudes {

    //Login validacion
     fun loginUser(contextoActual: Context, contextSiguiente: Class<out Activity>, correo: String, password: String, mensaje: String) {

        val url = "https://www.albanss.com/loginUser.login"
        val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    //Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                    try {
                        //val jsonObject = JSONObject(response) //full vector
                        val respuesta = JSONObject(response).getString("confirmado")
                        if (respuesta == "true") {
                            Toast.makeText(contextoActual, mensaje, Toast.LENGTH_LONG).show()

                            Handler(Looper.getMainLooper()).postDelayed({
                                val i = Intent(contextoActual, contextSiguiente)
                                contextoActual.startActivity(i)
                                (contextoActual.applicationContext as Activity).finish()

                            }, 2000)


                        } else if (respuesta == "error") {

                            Toast.makeText(contextoActual, "Datos No Validos", Toast.LENGTH_LONG).show()
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

}