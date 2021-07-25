package com.lakshyagupta7089.tictactoe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lakshyagupta7089.tictactoe.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
     private var binding: ActivitySplashBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding!!.textView.alpha = 0f
        binding!!.textView.animate()
            .translationY(binding!!.textView.height.toFloat())
            .alpha(1f)
            .setStartDelay(500).duration = 2000

        Handler().postDelayed({
            startActivity(
                Intent(
                    applicationContext, SelectTypeOfGame::class.java
                )
            )
            finish()
        }, 3000)
    }
}