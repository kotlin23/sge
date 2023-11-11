package com.kc.lesson2

import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Polygon


open class Star(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var width: Double = 0.0,
    var height: Double = 0.0,
    stroke: Paint = Color.BLACK, strokeWidth: Double = 1.0
) : Polygon() {

    fun repaint() {
        val dx = width
        val dy = height
        val star = arrayOf(
            x + dx / 2, y,
            dx * 8.2 / 13 + x, dy * 5 / 12.7 + y,
            dx * 13 / 13 + x, dy * 5 / 12.7 + y,
            dx * 9.1 / 13 + x, dy * 7.7 / 12.7 + y,
            dx * 10.7 / 13 + x, dy * 12.7 / 12.7 + y,
            dx * 6.5 / 13 + x, dy * 9.7 / 12.7 + y,
            dx * 2.3 / 13 + x, dy * 12.7 / 12.7 + y,
            dx * 3.9 / 13 + x, dy * 7.7 / 12.7 + y,
            dx * 0 / 13 + x, dy * 5 / 12.7 + y,
            dx * 4.8 / 13 + x, dy * 5 / 12.7 + y,
        )
        super.getPoints().clear()
        super.getPoints().addAll(star)
    }

    override fun toString(): String =
        "Star[x=$x, y=$y, width=$width, height=$height, stroke=$stroke, strokeWidth=$strokeWidth]"

    init {
        super.setStroke(stroke)
        super.setStrokeWidth(strokeWidth)
        fill = Color.TRANSPARENT
        repaint()
    }

}

