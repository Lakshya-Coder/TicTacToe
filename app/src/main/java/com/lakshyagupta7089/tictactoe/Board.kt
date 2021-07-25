package com.lakshyagupta7089.tictactoe

class Board {
    companion object {
        var PLAYER = "O"
        var COMPUTER = "X"
    }

    val board = Array(3) {
        arrayOfNulls<String>(3)
    }

    private val availableCells : List<Cell> get() {
        val cells = mutableListOf<Cell>()

        for (i in board.indices) {
            for (j in board.indices) {
                if (board[i][j].isNullOrEmpty()) {
                    cells.add(Cell(i, j))
                }
            }
        }

        return cells
    }

    val gameOver : Boolean
        get() = hasComputerWon() || hasPlayerWon() || availableCells.isEmpty()

    val cells = arrayOfNulls<Cell>(3)


    fun hasComputerWon() : Boolean {
        val first = (board[0][0] == board[1][1] &&
                board[0][0] == board[2][2] &&
                board[0][0] == COMPUTER);
        val second = (board[0][2] == board[1][1] &&
                board[0][2] == board[2][0] &&
                board[0][2] == COMPUTER);

        if (first) {
            cells[0] = Cell(0, 0)
            cells[1] = Cell(1, 1)
            cells[2] = Cell(2, 2)

            return true
        }

        if (second) {
            cells[0] = Cell(0, 2)
            cells[1] = Cell(1, 1)
            cells[2] = Cell(2, 0)

            return true
        }

        for (i in board.indices) {
            if (
                (board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == COMPUTER)) {
                    cells[0] = Cell(i, 0)
                    cells[1] = Cell(i, 1)
                    cells[2] = Cell(i, 2)

                    return true
            }

            if ((board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == COMPUTER)
            ) {
                cells[0] = Cell(0, i)
                cells[1] = Cell(1, i)
                cells[2] = Cell(2, i)

                return true
            }
        }

        return false
    }

    fun hasPlayerWon() : Boolean {
        val first = (board[0][0] == board[1][1] &&
                board[0][0] == board[2][2] &&
                board[0][0] == PLAYER);
        val second = (board[0][2] == board[1][1] &&
                board[0][2] == board[2][0] &&
                board[0][2] == PLAYER);

        if (first) {
            cells[0] = Cell(0, 0)
            cells[1] = Cell(1, 1)
            cells[2] = Cell(2, 2)

            return true
        }

        if (second) {
            cells[0] = Cell(0, 2)
            cells[1] = Cell(1, 1)
            cells[2] = Cell(2, 0)

            return true
        }

        for (i in board.indices) {
            if (
                (board[i][0] == board[i][1] &&
                        board[i][0] == board[i][2] &&
                        board[i][0] == PLAYER)) {
                cells[0] = Cell(i, 0)
                cells[1] = Cell(i, 1)
                cells[2] = Cell(i, 2)

                return true
            }

            if ((board[0][i] == board[1][i] &&
                        board[0][i] == board[2][i] &&
                        board[0][i] == PLAYER)
            ) {
                cells[0] = Cell(0, i)
                cells[1] = Cell(1, i)
                cells[2] = Cell(2, i)

                return true
            }
        }

        return false
    }

    var computersMove : Cell ?= null
    fun minimax(depth: Int, player: String) : Int {
        if (hasComputerWon()) return +1
        if (hasPlayerWon()) return -1
        if (availableCells.isEmpty()) return 0

        var min = Integer.MAX_VALUE;
        var max = Integer.MIN_VALUE;

        for (i in availableCells.indices) {
            val cell = availableCells[i];

            if (player == COMPUTER) {
                placeMove(cell, COMPUTER)
                val currentScore = minimax(depth + 1, PLAYER)

                max = Math.max(max, currentScore)

                if (currentScore >= 0) {
                    if (depth == 0) {
                        computersMove = cell
                    }
                }

                if (currentScore == 1) {
                    board[cell.i][cell.j] = ""
                    break
                }

                if (i == availableCells.size - 1 && max < 0) {
                    if (depth == 0) {
                        computersMove = cell
                    }
                }
            } else {
                placeMove(cell, PLAYER)
                val currentScore = minimax(depth + 1, COMPUTER)
                min = Math.min(min, currentScore)

                if (min == -1) {
                    board[cell.i][cell.j] = ""
                    break
                }
            }

            board[cell.i][cell.j] = ""
        }

        return if (player == COMPUTER) {
            max
        } else {
            min
        }
    }

    fun placeMove(cell: Cell, player: String) {
        board[cell.i][cell.j] = player
    }
}