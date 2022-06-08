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
import android.widget.Toast

class Registro : AppCompatActivity(), View.OnClickListener  {
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var repeatPassword: String

    private lateinit var bAceptar: Button
    private lateinit var bCancelar: Button

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)

        bAceptar = findViewById(R.id.bAceptar)
        bAceptar.setOnClickListener(this)

        bCancelar = findViewById(R.id.bCancelar)
        bCancelar.setOnClickListener(this)

        usernameEditText = findViewById(R.id.etUsuario)
        passwordEditText = findViewById(R.id.etPassword)
        repeatPasswordEditText = findViewById(R.id.etRepeatPassword)
    }

    override fun onClick(v: View?) {
        if (v?.id == bAceptar.id) {
            aceptar()
        } else {
            cancelar()
        }
    }

    private fun aceptar() {
        username = usernameEditText.text.toString()
        password = passwordEditText.text.toString()
        repeatPassword = repeatPasswordEditText.text.toString()

        var fieldBlankError: String = getString(R.string.err_fld_blank)
        var passwordsDoNotMatchError: String = getString(R.string.err_pass_do_not_match)

        if (username?.isNullOrEmpty()) {
            usernameEditText.error = fieldBlankError
            return
        } else if (password?.isNullOrEmpty()) {
            passwordEditText.error = fieldBlankError
            return
        } else if (repeatPassword?.isNullOrEmpty()) {
            repeatPasswordEditText.error = fieldBlankError
            return
        } else if (!password.equals(repeatPassword)) {
            repeatPasswordEditText.error = passwordsDoNotMatchError
            return
        } else {
            val proceso = ProcesoRegistro(this)
            proceso.execute(username)
        }
    }

    private fun cancelar() {
        val caller = intent
        setResult(Activity.RESULT_CANCELED, caller)
        finish()
    }

    inner class ProcesoRegistro(private var contexto: Context) :
        AsyncTask<String, Int, Long>() {
        private lateinit var progreso: ProgressDialog

        override fun onPreExecute() {
            progreso = ProgressDialog(contexto)
            progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progreso.setMessage(getString(R.string.procesando_registro))
            progreso.progress = 0
            progreso.show()
        }

        override fun doInBackground(vararg params: String?): Long {
            for (i in 1..2) {
                SystemClock.sleep(1000)
                publishProgress(i + 100 / 2)
            }

            intent.putExtra("usuario", params[0])
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
    }
}