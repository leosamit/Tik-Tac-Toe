package com.samdroid.leo.tictactoe.board

import com.samdroid.leo.tictactoe.data.DataManager
import com.samdroid.leo.tictactoe.data.model.AIPlayerMinimax
import com.samdroid.leo.tictactoe.data.model.Cell
import com.samdroid.leo.tictactoe.data.model.GameState
import com.samdroid.leo.tictactoe.data.model.Seed

class BoardPresenter(viewScreen: BoardView) {
    val dataManager: DataManager = DataManager()
    val computer = AIPlayerMinimax(dataManager.board)
    val view = viewScreen
    var computerFirst = false

    init {
        dataManager.setOnGameStatusChangeListener(object : DataManager.OnGameStatusChange {
            override fun onStatusChange(gameState: GameState) {
                view.gameStatus(gameState)
                view.winLine(dataManager.winLine)
            }
        })

    }

    fun selectPlayer(seed: Seed) {
        computer.setSeed(
            when (seed) {
                Seed.CROSS -> {
                    computerFirst = false
                    Seed.NOUGHT
                }
                else -> {
                    computerFirst = true
                    Seed.CROSS
                }
            }
        )

        view.initGame()
    }

    fun initGame() {
        dataManager.initGame()
        if (computerFirst) {
            view.reloadBoard(dataManager.playerMove(computer.mySeed, 0, 0))
        }
    }

    fun playerMove(row: Int, col: Int, player: Int) {
        when (player) {
            1 -> view.reloadBoard(dataManager.playerMove(computer.oppSeed, row, col))
            2 -> view.reloadBoard(dataManager.playerMove(computer.mySeed, row, col))


        }
    }

    fun computerMove() {
        val moves = computer.move()
        view.reloadBoard(dataManager.playerMove(computer.mySeed, moves!![0], moves[1]))
        view.onComputerMoved()
    }

    interface BoardView {
        fun reloadBoard(cell: Cell?)
        fun gameStatus(gameState: GameState)
        fun initGame()
        fun onComputerMoved()
        fun winLine(winLine: Int)
    }
}