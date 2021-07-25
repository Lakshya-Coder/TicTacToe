package com.lakshyagupta7089.tictactoe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lakshyagupta7089.tictactoe.databinding.ActivitySelectTypeOfGameBinding

class SelectTypeOfGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySelectTypeOfGameBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_select_type_of_game)
        binding.withAFriendButton.setOnClickListener {
            startActivity(
                Intent(this, TakePlayerNameActivity::class.java)
            )
            finish()
        }
        binding.withAiButton.setOnClickListener { view ->
            startActivity(
                Intent(this, ChoseXOrOForAi::class.java)
            )
            finish()
        }
    }
}