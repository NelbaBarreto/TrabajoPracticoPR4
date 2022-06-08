package com.columbia.trabajopractico

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var bHacerPedido: Button
    private lateinit var bRegistro: Button

    private lateinit var usuario: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bRegistro = findViewById(R.id.bRegistrarse)
        bRegistro.setOnClickListener(this)
    }

   override fun onClick(v: View?) {
        if (v?.id == bRegistro.id){
            lanzarRegistro()
        }
    }

   private val launcherRegistro =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                (it.data?.extras?.getString("usuario") ?: "").also { usuario = it }
                Toast.makeText(this, R.string.registro_exitoso, Toast.LENGTH_LONG).show()
            } else if (it.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, R.string.registro_cancelado, Toast.LENGTH_LONG).show()
            }
        }

    private fun lanzarRegistro() {
        val intent = Intent(this, Registro::class.java)
        launcherRegistro.launch(intent)
    }
}