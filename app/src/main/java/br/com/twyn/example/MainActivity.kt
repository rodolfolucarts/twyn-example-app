package br.com.twyn.example

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.twyn.capture.sdk.contracts.CaptureActivityContract
import br.com.twyn.capture.sdk.data.InputData
import br.com.twyn.capture.sdk.enums.TransactionType
import br.com.twyn.capture.sdk.services.StartCaptureService


class MainActivity : AppCompatActivity() {

    private var transactionType = TransactionType.ENROLLMENT
    private lateinit var codeCallback: String

    private lateinit var _context: Context

    private val pickCapture =
        registerForActivityResult(CaptureActivityContract()) { result ->
            println("result $result")
            Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show()
            result.toString()
        }


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

            StartCaptureService.capture(pickCapture, input)

//            pickCapture.launch(input)

        }
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

}