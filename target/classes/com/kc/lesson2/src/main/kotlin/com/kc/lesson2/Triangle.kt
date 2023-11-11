package com.kc.lesson2

import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Polygon

open class Triangle(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var width: Double = 0.0,
    var height: Double = 0.0,
    stroke: Paint = Color.BLACK, strokeWidth: Double = 1.0
) : Polygon() {

    fun repaint() {
        val dx = width
        val dy = height
        val tri = arrayOf( x, y, x, y+height, x+width, y+height)
        super.getPoints().clear()
        super.getPoints().addAll(tri)
    }

    override fun toString(): String =
        "Triangle[x=$x, y=$y, width=$width, height=$height, stroke=$stroke, strokeWidth=$strokeWidth]"

    init {
        super.setStroke(stroke)
        super.setStrokeWidth(strokeWidth)
        fill = Color.TRANSPARENT
        repaint()
    }

}
