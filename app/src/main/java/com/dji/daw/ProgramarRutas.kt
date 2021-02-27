package com.dji.daw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dji.daw.controles.AppCompatActivityFullScreen

class ProgramarRutas : AppCompatActivityFullScreen() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programar_rutas)
    }
}