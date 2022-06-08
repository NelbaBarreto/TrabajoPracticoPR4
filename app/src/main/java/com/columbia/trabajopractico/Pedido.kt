@file:Suppress("DEPRECATION")

package com.columbia.trabajopractico

import Modelos.Pizza
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class Pedido : AppCompatActivity(), View.OnClickListener {
    private lateinit var bAceptar: Button
    private lateinit var bCancelar: Button

    private lateinit var etCantidad: EditText
    private lateinit var spSabor: Spinner
    private lateinit var spTamano: Spinner

    private lateinit var cantidad: String
    private lateinit var sabor: String
    private lateinit var tamano: String

    lateinit var pizza: Pizza

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pedido)

        etCantidad = findViewById(R.id.etCantidad)
        etCantidad.setText("1")
        spSabor= findViewById(R.id.spSabor)
        spTamano= findViewById(R.id.spTamano)

        bAceptar = findViewById(R.id.bAceptar)
        bAceptar.setOnClickListener(this)

        bCancelar = findViewById(R.id.bCancelar)
        bCancelar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == bAceptar.id) {
            aceptar()
        } else {
            cancelar()
        }
    }

    private fun aceptar() {
        cantidad = etCantidad.text.toString()
        sabor = spSabor.selectedItem.toString()
        tamano = spTamano.selectedItem.toString()

        if (cantidad.toInt() <= 0 || cantidad.isNullOrEmpty()) {
            etCantidad.error = getString(R.string.err_can_cero)
        } else {
            pizza = Pizza(sabor, tamano, cantidad.toInt())

            val proceso = ProcesoPedido(this)
            proceso.execute(pizza)
        }
    }

    private fun cancelar() {
        val caller = intent
        setResult(Activity.RESULT_CANCELED, caller)
        finish()
    }

    inner class ProcesoPedido(private var contexto: Context) :
        AsyncTask<Pizza, Int, Long>() {
        private lateinit var progreso: ProgressDialog

        override fun onPreExecute() {
            progreso = ProgressDialog(contexto)
            progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progreso.setMessage(getString(R.string.mensaje_cancelar))
            progreso.setCancelable(true)
            progreso.setOnCancelListener { cancel(true) }
            progreso.progress = 0
            progreso.show()
        }

        override fun doInBackground(vararg params: Pizza?): Long {
            var cantidadSegundos = 15
            var i = 1

            while (i <= cantidadSegundos && !isCancelled) {
                SystemClock.sleep(1000)
                publishProgress(i + 100 / cantidadSegundos!!)
                i++
            }

            intent.putExtra("sabor", params[0]?.sabor)
            intent.putExtra("cantidad", params[0]?.cantidad.toString())
            intent.putExtra("tamanio", params[0]?.tamanio)
            return 1L
        }

        override fun onProgressUpdate(vararg values: Int?) {
            progreso.setProgress(values[0]!!)
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            progreso.dismiss()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        override fun onCancelled() {
            super.onCancelled()
            cancelar()
        }
    }
}