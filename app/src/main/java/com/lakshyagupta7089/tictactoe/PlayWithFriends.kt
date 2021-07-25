package com.lakshyagupta7089.tictactoe

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lakshyagupta7089.tictactoe.databinding.ActivityPlayWithFriendsBinding
import com.lakshyagupta7089.tictactoe.util.Constants
import java.util.*

class PlayWithFriends : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityPlayWithFriendsBinding ?= null
    private var playerOne: String? = null
    private var playerTwo: String? = null
    private val buttons = arrayOfNulls<Button>(9)

    private var playerOneScoreCount = 0
    private var playerTwoScoreCount = 0
    private var routCount = 0
    private var activePlayer = false

    private var map: HashMap<Int, String>? = null

    // p1 => 0
    // p2 => 1
    // empty => 3
    private var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

    private var winningPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_with_friends)

        setPlayersName()
        setThingsUp()

        binding!!.reset.setOnClickListener {
            val alertDialog =
                AlertDialog.Builder(this)
                    .setTitle("Reset game?")
                    .setMessage("Are you sure you want to reset game!")
                    .setPositiveButton(
                        "Yes"
                    ) { _: DialogInterface?, _: Int -> playAgain() }
                    .setNegativeButton(
                        "No"
                    ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
                    .create()
            alertDialog.show()
        }

        binding!!.exit.setOnClickListener {
            val alertDialog =
                AlertDialog.Builder(this)
                    .setTitle("Are you sure you?")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton(
                        "Yes"
                    ) { _: DialogInterface?, _: Int -> goToSelectTypeOfGame() }
                    .setNegativeButton(
                        "No"
                    ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
                    .create()
            alertDialog.show()
        }
    }

    private fun goToSelectTypeOfGame() {
        startActivity(
            Intent(
                this,
                SelectTypeOfGame::class.java
            )
        )
        finish()
    }

    private fun setThingsUp() {
        playerOneScoreCount = 0
        playerTwoScoreCount = 0
        routCount = 0
        activePlayer = true
        map = HashMap()
        map!![0] = getString(R.string.cross)
        map!![1] = getString(R.string.circle)
        val random = Random()
        activePlayer = random.nextInt() % 2 == 0
        updateUser()
        for (i in buttons.indices) {
            val buttonId = "button_$i"
            val resourceID = resources.getIdentifier(buttonId, "id", packageName)
            buttons[i] = findViewById(resourceID)
            buttons[i]!!.setOnClickListener(this)
        }
    }

    private fun updateUser() {
        if (activePlayer) {
            binding!!.playerStatusTextView.text = playerOne
        } else {
            binding!!.playerStatusTextView.text = playerTwo
        }
    }

    private fun setPlayersName() {
        playerOne = intent.getStringExtra(Constants.FIRST_PLAYER_NAME)
        playerTwo = intent.getStringExtra(Constants.SECOND_PLAYER_NAME)
        binding!!.playerOneTextView.text = playerOne
        binding!!.playerTwoTextView.text = playerTwo
    }

    override fun onClick(v: View) {
        if ((v as Button).text.toString().isEmpty()) {
            val buttonId = v.getResources().getResourceEntryName(v.getId())
            val gameStatePointer = buttonId.substring(buttonId.length - 1).toInt()
            if (activePlayer) {
                // ((Button) v).setText(R.string.cross);
                v.text =
                    map!![intent.getIntExtra(Constants.PICK_SIDE_FOR_PLAYER, 0)]
                v.setTextColor(Color.parseColor("#FFC34A"))
                gameState[gameStatePointer] = intent.getIntExtra(Constants.PICK_SIDE_FOR_PLAYER, 0)
            } else {
                // ((Button) v).setText(R.string.circle);
                v.text = map!![if (intent.getIntExtra(
                        Constants.PICK_SIDE_FOR_PLAYER, 0
                    ) == 0
                ) 1 else 0]
                v.setTextColor(Color.parseColor("#70FFEA"))
                gameState[gameStatePointer] = if (intent.getIntExtra(
                        Constants.PICK_SIDE_FOR_PLAYER, 0
                    ) == 0
                ) 1 else 0
            }
            routCount++
            when {
                checkWinner() -> {
                    for (button in buttons) {
                        button!!.isClickable = false
                    }
                    if (activePlayer) {
                        playerOneScoreCount++
                    } else {
                        playerTwoScoreCount++
                    }
                    Handler().postDelayed({ this.showDialog() }, 600)
                }
                routCount == 9 -> {
                    showDialog()
                }
                else -> {
                    activePlayer = !activePlayer
                    updateUser()
                }
            }
        }
    }

    private fun showDialog() {
        val alBuilder = AlertDialog.Builder(this)
        alBuilder.setTitle("Reset game")
        alBuilder.setMessage("Are you want to reset the game!")
        alBuilder.setCancelable(false)
        alBuilder.setPositiveButton(
            "Reset game"
        ) { _: DialogInterface?, _: Int -> playAgain() }
        alBuilder.setNegativeButton(
            "Exit"
        ) { _: DialogInterface?, _: Int -> finish() }
        val alertDialog = alBuilder.create()
        alertDialog.show()
        updatePlayerScore()
    }

    private fun checkWinner(): Boolean {
        var winnerResult = false
        for (winningPosition in winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                winnerResult = true
                for (j in winningPosition) {
                    buttons[j]!!.setTextColor(Color.WHITE)
                    buttons[j]!!.setBackgroundColor(Color.GREEN)
                }
                break
            }
        }
        return winnerResult
    }

    private fun updatePlayerScore() {
        binding!!.playerOneScoreTextView.text = playerOneScoreCount.toString()
        binding!!.playerTwoScoreTextView.text = playerTwoScoreCount.toString()
    }

    private fun playAgain() {
        routCount = 0
        activePlayer = playerOneScoreCount > playerTwoScoreCount
        for (i in buttons.indices) {
            gameState[i] = 2
            buttons[i]!!.text = ""
            buttons[i]!!.setBackgroundColor(Color.parseColor("#15123A"))
            buttons[i]!!.isClickable = true
        }
    }
}