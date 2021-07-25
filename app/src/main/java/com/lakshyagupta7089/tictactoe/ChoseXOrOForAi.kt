package com.lakshyagupta7089.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lakshyagupta7089.tictactoe.databinding.ActivityChoseXorOforAiBinding
import com.lakshyagupta7089.tictactoe.util.Constants

class ChoseXOrOForAi : AppCompatActivity() {
    private var binding: ActivityChoseXorOforAiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chose_xor_ofor_ai)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chose_xor_ofor_ai)
        binding!!.pickYourSide.text = String.format(
            "Pick your side for %s",
            intent.getStringExtra(Constants.FIRST_PLAYER_NAME)
        )
        binding!!.continueButton.setOnClickListener {
            val radioButton: RadioButton ?=
                findViewById(binding!!.radioGroup2.checkedRadioButtonId)

            if (radioButton != null) {
                val id =
                    radioButton.resources.getResourceEntryName(radioButton.id)
                val intent = Intent(this, MainActivity::class.java)
                if (id == "radioButton1") {
                    intent.putExtra(Constants.PICK_SIDE_FOR_PLAYER, 0)
                    intent.putExtra(Constants.SIDE_FOR_AI, 1)
                } else if (id == "radioButton2") {
                    intent.putExtra(Constants.PICK_SIDE_FOR_PLAYER, 1)
                    intent.putExtra(Constants.SIDE_FOR_AI, 0)
                }
                startActivity(intent)
                finish()
            }
        }
    }
}