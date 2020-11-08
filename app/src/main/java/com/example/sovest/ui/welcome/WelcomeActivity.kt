package com.example.sovest.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import com.example.sovest.MainActivity
import com.example.sovest.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        ed_monthly_income.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                val limit: Int = if (p0?.toString().isNullOrEmpty()) 0 else p0.toString().toInt()
                if (limit > 0) {
                    sb_limit.max = (limit * 0.99).toInt()
                    updateLimit()
                } else {
                    tv_limit.text = "0"
                }
            }
        })

        btn_OK.setOnClickListener {
            // send data to home fragment
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("LIMIT", tv_limit.text)
            }
            startActivity(intent)
        }
    }

    private fun updateLimit() {
        sb_limit.setOnSeekBarChangeListener(object : SimpleSeekBarChangeListener() {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv_limit.text = p1.toString()
                if (p1 > 0) {
                    btn_OK.isEnabled = true
                }
            }
        })
    }

    abstract class SimpleSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {
        override fun onStartTrackingTouch(p0: SeekBar) =
            Unit
        override fun onStopTrackingTouch(p0: SeekBar) =
            Unit
    }

    abstract class SimpleTextWatcher : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
            Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
            Unit
    }
}