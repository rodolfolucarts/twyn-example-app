package br.com.twyn.example

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import br.com.twyn.capture.sdk.data.InputData
import br.com.twyn.capture.sdk.enums.TransactionType
import br.com.twyn.capture.sdk.listeners.TransactionCompletedListener
import br.com.twyn.capture.sdk.services.StartCaptureService
import android.app.NotificationManager
import android.content.Context
import android.widget.Toast
import br.com.twyn.capture.sdk.listeners.TaskCompletedListener


class MainActivity : Activity(), TransactionCompletedListener{

    private var transactionType = TransactionType.ENROLLMENT
    private lateinit var codeCallback : String

    private lateinit var _context :Context



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button1);

        findViewById<RadioButton>(R.id.cadastrar).isChecked = true

        _context = this;

        btn.setOnClickListener {
            val input = InputData(
                document = findViewById<EditText>(R.id.cpf).text.toString(),
                authorizationCode = "SPPASS",
                type = transactionType,
                name = findViewById<EditText>(R.id.nome).text.toString(),
                email = findViewById<EditText>(R.id.email).text.toString(),
                mobilePhone = findViewById<EditText>(R.id.celular).text.toString(),
                dateOfBirth = findViewById<EditText>(R.id.dataNascimento).text.toString()
            )
            println(input.type.toString())

            val service = StartCaptureService()



            service.startCapture(this, input,
//                object : TransactionCompletedListener {
//                    override fun onTransactionComplete(code: String, message: String) {
//                        sendMessage(code, message)
//                    }
//                }
                        this)
        }

    }


    fun sendMessage(code: String, message: String) {
        Toast.makeText(this._context, "message", Toast.LENGTH_LONG).show()
    }

    fun radio_button_click(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.id) {
                R.id.cadastrar -> hideFields(!checked)
                R.id.verificar -> hideFields(checked)
            }
        }
    }

    fun hideFields(hide: Boolean) {

        var visibility = 0

        if (hide) {
            transactionType = TransactionType.VERIFICATION
            visibility = View.GONE
        } else {
            transactionType = TransactionType.ENROLLMENT
            visibility = View.VISIBLE
        }

        findViewById<EditText>(R.id.nome).visibility = visibility
        findViewById<EditText>(R.id.email).visibility = visibility
        findViewById<EditText>(R.id.celular).visibility = visibility
        findViewById<EditText>(R.id.dataNascimento).visibility = visibility

    }

    override fun onTransactionComplete(code: String, message: String) {
        sendMessage(code, message)
    }


}