package com.lakshyagupta7089.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lakshyagupta7089.tictactoe.databinding.ActivityChoseXorOforFriendsBinding
import com.lakshyagupta7089.tictactoe.util.Constants

class ChoseXOrOActivityForFriends : AppCompatActivity() {
    private var binding: ActivityChoseXorOforFriendsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_chose_xor_ofor_friends)
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
                val intent = Intent(this, PlayWithFriends::class.java)
                if (id == "radioButton1") {
                    intent.putExtra(Constants.PICK_SIDE_FOR_PLAYER, 0)
                } else if (id == "radioButton2") {
                    intent.putExtra(Constants.PICK_SIDE_FOR_PLAYER, 1)
                }

                intent.putExtra(
                    Constants.FIRST_PLAYER_NAME,
                    getIntent().getStringExtra(Constants.FIRST_PLAYER_NAME)
                )
                intent.putExtra(
                    Constants.SECOND_PLAYER_NAME,
                    getIntent().getStringExtra(Constants.SECOND_PLAYER_NAME)
                )
                startActivity(intent)
                finish()
            }
        }
    }
}