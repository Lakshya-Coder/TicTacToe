package com.lakshyagupta7089.tictactoe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lakshyagupta7089.tictactoe.databinding.ActivityTakePlayerNameBinding
import com.lakshyagupta7089.tictactoe.util.Constants

class TakePlayerNameActivity : AppCompatActivity() {
    private var binding: ActivityTakePlayerNameBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_take_player_name)
        binding!!.next.setOnClickListener { view: View -> handleNextButton(view) }
    }

    private fun handleNextButton(view: View) {
        if (binding!!.firstPlayerName.text.toString().isEmpty() ||
            binding!!.secondPlayerName.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "First fill the following", Toast.LENGTH_SHORT).show()
            return
        }
        goToMainActivity()
    }

    private fun goToMainActivity() {
        val firstPlayerName = binding!!.firstPlayerName.text.toString()
        val secondPlayerName = binding!!.secondPlayerName.text.toString()
        val intent = Intent(applicationContext, ChoseXOrOActivityForFriends::class.java)
        intent.putExtra(Constants.FIRST_PLAYER_NAME, firstPlayerName)
        intent.putExtra(Constants.SECOND_PLAYER_NAME, secondPlayerName)
        startActivity(intent)
        finish()
    }
}