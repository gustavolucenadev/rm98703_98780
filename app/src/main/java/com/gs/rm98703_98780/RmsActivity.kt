package com.gs.rm98703_98780


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RmsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rm)

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }
    }
}