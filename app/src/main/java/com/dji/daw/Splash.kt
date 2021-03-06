package com.dji.daw

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dji.daw.controles.AppCompatActivityFullScreen

class Splash :AppCompatActivityFullScreen() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        this.supportActionBar?.hide()

        splashSleep()
    }


    private fun splashSleep(){

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this@Splash, Login::class.java)
            startActivity(i) //start new activity
            finish()
        }, 5000)

    }


}