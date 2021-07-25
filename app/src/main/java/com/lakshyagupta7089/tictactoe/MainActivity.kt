package com.lakshyagupta7089.tictactoe

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import com.lakshyagupta7089.tictactoe.databinding.ActivityMainBinding
import com.lakshyagupta7089.tictactoe.util.Constants

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val boardCells = Array(3) {
        arrayOfNulls<ImageView>(
            3
        )
    }
    var board =  Board()
    private var aiScore = 0;
    private var playerScore = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        if (intent.getIntExtra(Constants.PICK_SIDE_FOR_PLAYER, 0) == 0) {
            Board.PLAYER = "X"
            Board.PLAYER = "O"
        }

        loadBoard()
        binding!!.buttonRestart.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Reset game?")
                .setMessage("Are you sure you want to reset game!")
                .setPositiveButton("Yes") { _: DialogInterface?, _: Int -> playAgain() }
                .setNegativeButton("No") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
                .create()

            alertDialog.show()
        }

        binding!!.exit.setOnClickListener {
            val alertDialog =
                AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
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


    private fun mapBoardToUi() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    Board.PLAYER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.o)
                        boardCells[i][j]?.isEnabled = false
                    }

                    Board.COMPUTER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.x)
                        boardCells[i][j]?.isEnabled = false
                    }

                    else -> {
                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled = true
                    }
                }
            }
        }
    }

    private fun loadBoard() {
        for (i in boardCells.indices) {
            for (j in boardCells[i].indices) {
                setUpAButton(i, j)
            }
        }
    }

    private fun setUpAButton(i: Int, j: Int) {
        boardCells[i][j] = ImageView(applicationContext)
        boardCells[i][j]!!.layoutParams = getLayoutParams(i, j)
        boardCells[i][j]!!.setBackgroundColor(Color.parseColor("#15123A"))
        boardCells[i][j]!!.setOnClickListener(CellClickListener(i, j))
        boardCells[i][j]!!.setPadding(10)

        binding?.layoutBoard?.addView(boardCells[i][j])
    }

    private fun getLayoutParams(i: Int, j: Int): GridLayout.LayoutParams {
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(i)
        layoutParams.columnSpec = GridLayout.spec(j)
        layoutParams.width = 250
        layoutParams.height = 250
        layoutParams.bottomMargin = 5
        layoutParams.topMargin = 5
        layoutParams.rightMargin = 5
        layoutParams.leftMargin = 5

        return layoutParams
    }

    inner class CellClickListener(
        private val i: Int,
        private val j: Int ) : View.OnClickListener {

        override fun onClick(v: View?) {
            if (!board.gameOver) {
                val cell = Cell(i, j)
                board.placeMove(cell, Board.PLAYER)

                board.minimax(0, Board.COMPUTER)

                board.computersMove?.let {
                    board.placeMove(it, Board.COMPUTER)
                }

                mapBoardToUi()
            }


            when {
                board.hasComputerWon() -> {
                    aiScore++
                    updateScore()
                    for (i in boardCells.indices) {
                        for (j in boardCells.indices) {
                            boardCells[i][j]!!.isClickable = false
                        }
                    }

                    board.cells.let {
                        boardCells[it[0]?.i!!][it[0]?.j!!]!!.setBackgroundColor(Color.GREEN)
                        boardCells[it[1]?.i!!][it[1]?.j!!]!!.setBackgroundColor(Color.GREEN)
                        boardCells[it[2]?.i!!][it[2]?.j!!]!!.setBackgroundColor(Color.GREEN)
                    }

                    // binding!!.textViewResult.text = getString(R.string.computer_won)
                    Handler().postDelayed({
                        showDialog("Reset game", "Are you want to reset the game?")
                    }, 600)
                }
                board.hasPlayerWon() -> {
                    playerScore++
                    updateScore()

                    for (i in boardCells.indices) {
                        for (j in boardCells.indices) {
                            boardCells[i][j]!!.isClickable = false
                        }
                    }

                    board.cells.let {
                        boardCells[it[0]?.i!!][it[0]?.j!!]!!.setBackgroundColor(Color.GREEN)
                        boardCells[it[1]?.i!!][it[1]?.j!!]!!.setBackgroundColor(Color.GREEN)
                        boardCells[it[2]?.i!!][it[2]?.j!!]!!.setBackgroundColor(Color.GREEN)
                    }

                    Handler().postDelayed({
                        showDialog("Reset game", "Are you want to reset the game?")
                    }, 600)
                }
                board.gameOver -> {
                    showDialog("Reset game", "Are you want to reset the game?");
                }
            }
        }

    }

    private fun updateScore() {
        binding!!.textViewAiScore.text = aiScore.toString()
        binding!!.textViewMeScore.text = playerScore.toString()
    }

    private fun showDialog(title: String, message: String) {
        val alBuilder = AlertDialog.Builder(this)
        alBuilder.setTitle(title)
        alBuilder.setMessage(message)
        alBuilder.setCancelable(false)
        alBuilder.setPositiveButton(
            "Reset game"
        ) { _: DialogInterface?, _: Int -> playAgain() }
        alBuilder.setNegativeButton(
            "Exit"
        ) { _: DialogInterface?, _: Int -> finish() }
        val alertDialog = alBuilder.create()
        alertDialog.show()
        // updatePlayerScore()
    }

    private fun playAgain() {
        board = Board()
        // binding!!.textViewResult.text = ""

        for (i in boardCells.indices) {
            for (j in boardCells.indices) {
                boardCells[i][j]!!.setBackgroundColor(Color.parseColor("#15123A"))
                boardCells[i][j]!!.isClickable = true
            }
        }

        mapBoardToUi()
    }

}