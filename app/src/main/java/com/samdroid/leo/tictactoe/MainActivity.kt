package com.samdroid.leo.tictactoe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import com.samdroid.leo.tictactoe.board.BoardPresenter
import com.samdroid.leo.tictactoe.data.model.Board
import com.samdroid.leo.tictactoe.data.model.Cell
import com.samdroid.leo.tictactoe.data.model.GameState
import com.samdroid.leo.tictactoe.data.model.Seed
import com.samdroid.leo.tictactoe.utils.Animations
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BoardPresenter.BoardView {

    override fun winLine(winLine: Int) {
        var pattern = Board().winningPatterns
        when (winLine) {
            pattern[0] -> bottom_horizontal.visibility = View.VISIBLE

            pattern[1] -> center_horizontal.visibility = View.VISIBLE

            pattern[2] -> top_horizontal.visibility = View.VISIBLE
            pattern[3] -> right_vertical.visibility = View.VISIBLE
            pattern[4] -> center_vertical.visibility = View.VISIBLE
            pattern[5] -> left_vertical.visibility = View.VISIBLE
            pattern[6] -> left_right_diagonal.visibility = View.VISIBLE
            pattern[7] -> right_left_diagonal.visibility = View.VISIBLE

        }
    }

    override fun reloadBoard(cell: Cell?) {
        Log.d(tag, "Reload Board")

        val unwrapCell = cell ?: Cell(-1, -1)
        Log.d(tag, "Row: " + unwrapCell.row.toString())
        Log.d(tag, "Column: " + unwrapCell.col.toString())
        Log.d(tag, "Column: " + unwrapCell.content.toString())

        val sectionView: View? = when {
            unwrapCell.row == 0 && unwrapCell.col == 0 -> {
                bu1.setBackgroundResource(
                    when (unwrapCell.content) {
                        Seed.CROSS -> R.mipmap.cross

                        else -> R.mipmap.circle
                    }
                )
                bu1
            }

            unwrapCell.row == 0 && unwrapCell.col == 1 -> {
                bu2.setBackgroundResource(
                    when (unwrapCell.content) {
                        Seed.CROSS -> R.mipmap.cross
                        else -> R.mipmap.circle
                    }
                )
                bu2
            }

            unwrapCell.row == 0 && unwrapCell.col == 2 -> {
                bu3.setBackgroundResource(
                    when (unwrapCell.content) {
                        Seed.CROSS -> R.mipmap.cross
                        else -> R.mipmap.circle
                    }
                )
                bu3
            }

            unwrapCell.row == 1 && unwrapCell.col == 0 -> {
                bu4.setBackgroundResource(
                    when (unwrapCell.content) {
                        Seed.CROSS -> R.mipmap.cross
                        else -> R.mipmap.circle
                    }
                )
                bu4
            }

            unwrapCell.row == 1 && unwrapCell.col == 1 -> {
                bu5.setBackgroundResource(
                    when (unwrapCell.content) {
                        Seed.CROSS -> R.mipmap.cross
                        else -> R.mipmap.circle
                    }
                )
                bu5
            }

            unwrapCell.row == 1 && unwrapCell.col == 2 -> {
                bu6.setBackgroundResource(
                    when (unwrapCell.content) {
                        Seed.CROSS -> R.mipmap.cross
                        else -> R.mipmap.circle
                    }
                )
                bu6
            }

            unwrapCell.row == 2 && unwrapCell.col == 0 -> {
                bu7.setBackgroundResource(
                    when (unwrapCell.content) {
                        Seed.CROSS -> R.mipmap.cross
                        else -> R.mipmap.circle
                    }
                )
                bu7
            }

            unwrapCell.row == 2 && unwrapCell.col == 1 -> {
                bu8.setBackgroundResource(
                    when (unwrapCell.content) {
                        Seed.CROSS -> R.mipmap.cross
                        else -> R.mipmap.circle
                    }
                )
                bu8
            }

            unwrapCell.row == 2 && unwrapCell.col == 2 -> {
                bu9.setBackgroundResource(
                    when (unwrapCell.content) {
                        Seed.CROSS -> R.mipmap.cross
                        else -> R.mipmap.circle
                    }
                )
                bu9
            }

            unwrapCell.row == -1 && unwrapCell.col == -1 -> {
                Log.d("BoardActivity", "Invalid position")
                return
            }
            else -> {
                return
            }
        }

        sectionView?.isEnabled = false
        sectionView?.animation = Animations.fadeIn(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                sectionView?.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }

    override fun gameStatus(gameState: GameState) {
        //Log.d(tag, gameState.toString())

        var result: String? = null
        var emoji: Int? = 0x0
        //
        when (gameState) {
            GameState.PLAYING -> return
            GameState.DRAW -> {
                emoji = 0x1f644
                display_board.text = "Its a draw"
                btn_replay.visibility = View.VISIBLE
                for (button in buttonList) {
                    button.isEnabled = false
                }
            }
            GameState.CROSS_WON -> {
                emoji = 0x1F60E
                display_board.text = "Cross has won"
                btn_replay.visibility = View.VISIBLE

                for (button in buttonList) {
                    button.isEnabled = false
                }
            }
            GameState.NOUGHT_WON -> {
                emoji = 0x1F60E
                btn_replay.visibility = View.VISIBLE
                display_board.text = "Nought has won"
                for (button in buttonList) {
                    button.isEnabled = false
                }

            }
        }


    }

    override fun initGame() {
        presenter.initGame()

    }

    override fun onComputerMoved() {
        progress_circular.visibility = View.GONE
    }

    var fabAddDisplay = false
    var computerPlay = false
    var buttonList = ArrayList<Button>()
    var crossLineList = ArrayList<View>()

    var activePlayer = 1

    val tag = "MainActivityLog"

    val presenter = BoardPresenter(this)
    val intentTag = "ComputerPlay"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.selectPlayer(Seed.CROSS)

        computerPlay = intent.getBooleanExtra(intentTag, false)
        Log.d(tag, computerPlay.toString())

        btn_replay.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra(intentTag, computerPlay)
            startActivity(intent)
        }
        for (button in 1..10) {
            buttonList.add(bu1)
            buttonList.add(bu2)
            buttonList.add(bu3)
            buttonList.add(bu4)
            buttonList.add(bu5)
            buttonList.add(bu6)
            buttonList.add(bu7)
            buttonList.add(bu8)
            buttonList.add(bu9)
        }

        fab_open.setOnClickListener {
            if (fabAddDisplay) {
                fab_user.visibility = View.GONE
                fab_computer.visibility = View.GONE
                fabAddDisplay = false

            } else {
                fab_user.visibility = View.VISIBLE
                fab_computer.visibility = View.VISIBLE
                fabAddDisplay = true
            }
        }
        fab_user.setOnClickListener {
            fabAddDisplay = false
            fab_user.visibility = View.GONE
            fab_computer.visibility = View.GONE
            if (computerPlay) {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra(intentTag, false)
                startActivity(intent)
            } else {
                fab_user.visibility = View.GONE
                fab_computer.visibility = View.GONE
            }
        }
        fab_computer.setOnClickListener {
            fabAddDisplay = false
            fab_user.visibility = View.GONE
            fab_computer.visibility = View.GONE
            if (computerPlay) {
                fab_user.visibility = View.GONE
                fab_computer.visibility = View.GONE
            } else {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra(intentTag, true)
                startActivity(intent)
            }
        }

    }

    fun buClick(view: View) {
        val buSelected = view as Button
        if (!computerPlay) {
            activePlayer = if (activePlayer == 1) {
                2
            } else 1
        }
        when (buSelected.id) {
            R.id.bu1 -> {
                presenter.playerMove(0, 0, activePlayer)
            }

            R.id.bu2 -> {
                presenter.playerMove(0, 1, activePlayer)
            }
            R.id.bu3 -> {
                presenter.playerMove(0, 2, activePlayer)
            }
            R.id.bu4 -> {
                presenter.playerMove(1, 0, activePlayer)
            }
            R.id.bu5 -> {
                presenter.playerMove(1, 1, activePlayer)

            }
            R.id.bu6 -> {
                presenter.playerMove(1, 2, activePlayer)

            }
            R.id.bu7 -> {
                presenter.playerMove(2, 0, activePlayer)

            }
            R.id.bu8 -> {
                presenter.playerMove(2, 1, activePlayer)

            }
            R.id.bu9 -> {
                presenter.playerMove(2, 2, activePlayer)
            }

        }
        if (computerPlay) {
            progress_circular.visibility = View.VISIBLE

            Handler().postDelayed({
                presenter.computerMove()
            }, 500)
        }

    }

}
