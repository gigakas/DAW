package com.dji.daw.db

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dji.daw.Bienvenidos
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class VolleySolicitudes {

    //Login validacion
     fun loginUser(context: Context, correo: String, password: String) {

        val LOGINURL = "https://www.albanss.com/loginUser.login"
        val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, LOGINURL,
                Response.Listener { response ->
                    //Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                    try {
                        //val jsonObject = JSONObject(response) //full vector
                        val respuesta = JSONObject(response).getString("confirmado")
                        if(respuesta == "true"){
                            Toast.makeText(context, "Bienvenido", Toast.LENGTH_LONG).show()
                            val i = Intent(context, Bienvenidos::class.java)
                            context.startActivity(i)

                        }else if(respuesta == "error"){

                            Toast.makeText(context, "Datos No Validos", Toast.LENGTH_LONG).show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //parametros a enviar por post o get
                params["correo"] = correo
                params["password"] = password
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }






}