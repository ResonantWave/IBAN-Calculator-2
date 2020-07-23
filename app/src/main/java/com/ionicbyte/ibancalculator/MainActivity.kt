package com.ionicbyte.ibancalculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    private var iban = ""
    private var i = 0
    private lateinit var adView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        adView = findViewById<View>(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val ibanInput = findViewById<EditText>(R.id.editText1)
        val displayIban = findViewById<TextView>(R.id.textView1)
        val buttonClipboard = findViewById<Button>(R.id.button1)
        val buttonClear = findViewById<Button>(R.id.buttonDelete)

        buttonClipboard.visibility = View.GONE
        buttonClear.visibility = View.GONE

        buttonClipboard.setOnClickListener {
            copyClipboard(iban)
        }

        buttonClear.setOnClickListener {
            ibanInput.setText("")
            buttonClear.visibility = View.GONE
        }

        ibanInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length < 20) {
                    displayIban.text = getString(R.string.remainingNumbers, (20 - s.length))
                    buttonClipboard.visibility = View.GONE
                } else {
                    val ccc = ibanInput.text.toString()
                    if (ccc.substring(i, i + 1).matches("[1234567890]".toRegex())
                        && CCC.validateCCC(ccc)) {

                        iban = IBAN.getIban("ES", ccc.substring(0, 4),
                            ccc.substring(4, 8), ccc.substring(8, 10),
                            ccc.substring(10, 20))

                        displayIban.text = getString(R.string.result, iban)
                        buttonClipboard.visibility = View.VISIBLE
                        buttonClear.visibility = View.VISIBLE
                    } else {
                        displayIban.text = getString(R.string.invalidCcc)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun copyClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("IBAN", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(applicationContext, getString(R.string.copied), Toast.LENGTH_LONG).show()
    }
}