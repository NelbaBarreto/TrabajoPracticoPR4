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

    private var usuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bRegistro = findViewById(R.id.bRegistrarse)
        bRegistro.setOnClickListener(this)

        bHacerPedido = findViewById(R.id.bHacerPedido)
        bHacerPedido.setOnClickListener(this)
    }

   override fun onClick(v: View?) {
        if (v?.id == bRegistro.id){
            lanzarRegistro()
        } else if (v?.id == bHacerPedido.id) {
            if (usuario?.isNullOrEmpty()) {
                mostrarMensaje(getString(R.string.err_mst_register))
            } else {
                mostrarMensaje("a")
               // lanzarHacerPedido()
            }
        }
    }

   private val launcherRegistro =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (it.data?.extras?.getString("usuario") ?: "").also { usuario = it }
            mostrarMensaje(getString(R.string.registro_exitoso))
        } else if (it.resultCode == Activity.RESULT_CANCELED) {
            mostrarMensaje(getString(R.string.registro_cancelado))
        }
    }

    private fun lanzarRegistro() {
        val intent = Intent(this, Registro::class.java)
        launcherRegistro.launch(intent)
    }

    private fun lanzarHacerPedido() {
        val intent = Intent(this, Pedido::class.java)
        //launcherRegistro.launch(intent)
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}