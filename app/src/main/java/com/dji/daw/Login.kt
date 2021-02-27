package com.dji.daw

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dji.daw.controles.DJIControl
import com.dji.daw.controles.AppCompatActivityFullScreen
import com.dji.daw.controles.PermisosApp


class Login : AppCompatActivityFullScreen(), View.OnClickListener {

    protected var loginBtn: Button? = null
    protected var logoutBtn: Button? = null
    protected var bindingStateTV: TextView? = null
    protected var appActivationStateTV: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        initUI()

        PermisosApp(this@Login).validarPermisos()
        DJIControl(this@Login).startSDKRegistration()



/////////////////////////////////////////////////////////


        /*val btnIngresar = findViewById<Button>(R.id.buttonLogin)
        btnIngresar.setOnClickListener {

            Toast.makeText(this@Login, "Pulse login.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Login, Bienvenidos::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

        val btnRegistroUsuario = findViewById<Button>(R.id.buttonRegistroNuevoUsuario)
        btnRegistroUsuario.setOnClickListener {

            Toast.makeText(this@Login, "Pulse registro", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Login, RegistroUsuario::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }*/

    }

    /////////////////////////////////////////////////////////////////////////////


    public override fun onResume() {
        Log.e(TAG, "onResume")
        super.onResume()

    }

    public override fun onPause() {
        Log.e(TAG, "onPause")
        super.onPause()

    }

    public override fun onStop() {
        Log.e(TAG, "onStop")
        super.onStop()
    }

    fun onReturn() {
        Log.e(TAG, "onReturn")
        finish()

    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }


    private fun initUI() {
        loginBtn = findViewById<View>(R.id.buttonLogin) as Button
        loginBtn!!.setOnClickListener(this)

    }

    override fun onClick(v:View) {
        when (v.id) {
            R.id.buttonLogin -> {
                Toast.makeText(this@Login, "Pulse login.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Login, Bienvenidos::class.java)
                    startActivity(i) //start new activity
                    finish()
                }, 3000)
            }else -> {
            }
        }
    }


    companion object {
        private val TAG = Login::class.java.name
    }


}