package com.davedecastro.yonduandroidexam.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.davedecastro.yonduandroidexam.R
import com.davedecastro.yonduandroidexam.data.db.entities.Seatmap
import com.davedecastro.yonduandroidexam.data.db.entities.SeatmapEvent
import com.davedecastro.yonduandroidexam.ui.schedule.SeatsInterface

class SeatmapCustomLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var paintTool: Paint = Paint()
    var textTool: Paint = Paint()
    lateinit var canvas: Canvas
    var isTablet = resources.getBoolean(R.bool.isTablet)

    var seatmap: Seatmap? = null

    var seatsInterface: SeatsInterface? = null

    fun setSeatmapData(seatmap: Seatmap) {
        this.seatmap = seatmap
    }

    fun getSeatmapData(): Seatmap? {
        return this.seatmap
    }

    private val seatmapEvents = mutableListOf<SeatmapEvent>()
    private val selectedSeatmap = mutableListOf<String>()

    private var seatmapStopX = 0F
    private var seatmapStopY = 0F

    private var seatmapEventX = 0F
    private var seatmapEventY = 0F

    init {
        paintTool.isAntiAlias = true
        paintTool.style = Paint.Style.STROKE
        textTool.textSize = 25F
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas
        drawMovieScreen(canvas)
        var top = 130F
        var bottom = 150F

        if (isTablet) {
            bottom += 60F
        }

        for ((index, value) in seatmap!!.seatmap.withIndex()) {
            if (index != 0) {
                if (isTablet) {
                    top = bottom + 10F
                    bottom = top + 60F
                } else {
                    top = bottom + 10F
                    bottom = top + 20F
                }
            }
            drawRow(canvas, rowLetter(index), top, bottom, index)
        }
    }

    private fun rowLetter(index: Int): String {
        when (index) {
            0 -> return "A"
            1 -> return "B"
            2 -> return "C"
            3 -> return "D"
            4 -> return "E"
            5 -> return "F"
            6 -> return "G"
            7 -> return "H"
            8 -> return "I"
            9 -> return "J"
            10 -> return "K"
            11 -> return "L"
            12 -> return "M"
            13 -> return "N"
            14 -> return "O"
            15 -> return "P"
            16 -> return "Q"
            17 -> return "R"
            18 -> return "S"
            19 -> return "T"
            20 -> return "U"
            21 -> return "V"
            22 -> return "W"
            23 -> return "X"
            24 -> return "Y"
            25 -> return "Z"
            else -> return ""
        }
    }

    private fun drawRow(canvas: Canvas?, letter: String, top: Float, bottom: Float, index: Int) {
        canvas?.drawText(letter, 0F, bottom, textTool)
        var left = 100F
        var right = 120F
        if (index == 0) {
            if (isTablet) {
                left = 75F
                right = 95F
            } else {
                left = 75F
                right = 95F
            }
        }
        canvas?.drawRect(left, top, right, bottom, paintTool)
        var seatmapRow = seatmap!!.seatmap[index]
        for (x in seatmapRow.indices) {
            if (seatmapRow[x] == "A(30)" || seatmapRow[x] == "a(30)") {
                if (isTablet) {
                    left = right + 5F
                    right = left + 20F
                } else {
                    left = right + 5F
                    right = left + 20F
                }
            } else {
                if (isTablet) {
                    left = right + 5F
                    right = left + 20F
                } else {
                    left = right + 5F
                    right = left + 20F
                }
                if (!seatmap!!.available.seats.contains(seatmapRow[x])) {
                    var paintTool: Paint = Paint()
                    paintTool.style = Paint.Style.FILL
                    canvas?.drawRect(left, top, right, bottom, paintTool)
                } else {
                    if (seatmapStopX != 0F) {
                        if (seatmapEvents.isNotEmpty()) {
                            for ((eventX, eventY) in seatmapEvents) {
                                if (eventX in left..right && eventY in top..bottom) {
                                    val paintTool: Paint = Paint()
                                    paintTool.color = Color.RED
                                    paintTool.style = Paint.Style.FILL
                                    canvas?.drawRect(left, top, right, bottom, paintTool)
                                    if (!selectedSeatmap.contains(seatmapRow[x])) {
                                        selectedSeatmap.add(seatmapRow[x])
                                        seatsInterface?.onSeatSelected(selectedSeatmap)
                                    }
                                } else {
                                    canvas?.drawRect(left, top, right, bottom, paintTool)
                                }
                            }
                        } else {
                            canvas?.drawRect(left, top, right, bottom, paintTool)
                        }
                    } else {
                        canvas?.drawRect(left, top, right, bottom, paintTool)
                    }
                }
                if (x == seatmapRow.size - 1) {
                    seatmapStopX = right
                    seatmapStopY = bottom
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                seatmapEventX = event.x
                seatmapEventY = event.y
                if ((seatmapEventX in 75F..seatmapStopX && seatmapEventY in 130F..seatmapStopY)) {
                    seatmapEvents.add(SeatmapEvent(seatmapEventX, seatmapEventY))
                    invalidate()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun drawMovieScreen(canvas: Canvas?) {
        var textTool: Paint = Paint()
        textTool.textSize = 60F
        canvas?.drawRect(0F, 20F, width.toFloat(), 100F, paintTool)
        if (isTablet) {
            canvas?.drawText("Movie Screen", 750F, 80F, textTool)
        } else {
            canvas?.drawText("Movie Screen", 350F, 80F, textTool)
        }
    }

}