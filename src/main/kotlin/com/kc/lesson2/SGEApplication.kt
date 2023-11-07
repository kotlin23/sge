package com.kc.lesson2

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import kotlin.math.sqrt

class SGEApplication : Application() {
    override fun start(stage: Stage) {
        var mode = MODE_PENCIL
        var clickCount = 0
        var x1 = 0.0
        var x2 = 0.0
        var y1 = 0.0
        var y2 = 0.0
        val canvas = Canvas(500.0, 500.0)
        val buttonRectangle = Button("Rectangle")
        val buttonTriangle = Button("Triangle")
        val buttonLine = Button("Line")
        val buttonCircle = Button("Circle")
        val buttonPencil = Button("Pencil")
        val label = Label("Pencil!")
        val flowPane = FlowPane()
        flowPane.getChildren().addAll(
                buttonPencil,
                buttonRectangle,
                buttonTriangle,
                buttonLine,
                buttonCircle,
                label,
                canvas,
        )
        flowPane.alignment = Pos.TOP_LEFT
        val graphicsContext = canvas.graphicsContext2D
//        graphicsContext.fill = Color.WHITE
//        graphicsContext.fillRect(0.0, 100.0, 400.0, 400.0)
        stage.scene = Scene(flowPane)

        buttonPencil.setOnAction(EventHandler<ActionEvent> {
            mode = MODE_PENCIL
            label.text = "Pencil!"
        })

        buttonRectangle.setOnAction(EventHandler<ActionEvent> {
            mode = MODE_RECTANGLE
            label.text = "Draw rectangle. Click first point!"
        })

        buttonTriangle.setOnAction(EventHandler<ActionEvent> {
            mode = MODE_TRIANGLE
            label.text = "Draw triangle. Click first point!"
        })

        buttonLine.setOnAction(EventHandler<ActionEvent> {
            mode = MODE_LINE
            label.text = "Draw line. Click first point!"
        })

        buttonCircle.setOnAction(EventHandler<ActionEvent> {
            mode = MODE_CIRCLE
            label.text = "Draw circle. Click center point!"
        })

        val mouseClicked = EventHandler { e: MouseEvent ->
            if (mode == MODE_RECTANGLE) {
                if (clickCount == 0) {
                    x1 = e.getX()
                    y1 = e.getY()
                    clickCount++
                    label.text = "Draw rectangle. Click second point!"
                } else {
                    graphicsContext.beginPath()
                    graphicsContext.moveTo(e.getX(), e.getY())
                    graphicsContext.lineTo(e.getX(), y1)
                    graphicsContext.lineTo(x1, y1)
                    graphicsContext.lineTo(x1, e.getY())
                    graphicsContext.lineTo(e.getX(), e.getY())
                    graphicsContext.stroke()
                    graphicsContext.closePath()
                    clickCount = 0
                    label.text = "Draw rectangle. Click first point!"
                }
            } else if (mode == MODE_TRIANGLE) {
                if (clickCount == 0) {
                    x1 = e.getX()
                    y1 = e.getY()
                    clickCount++
                    label.text = "Draw triangle. Click second point!"
                } else if (clickCount == 1) {
                    x2 = e.getX()
                    y2 = e.getY()
                    clickCount++
                    label.text = "Draw triangle. Click third point!"
                } else {
                    graphicsContext.beginPath()
                    graphicsContext.moveTo(e.getX(), e.getY())
                    graphicsContext.lineTo(x1, y1)
                    graphicsContext.lineTo(x2, y2)
                    graphicsContext.lineTo(e.getX(), e.getY())
                    graphicsContext.stroke()
                    graphicsContext.closePath()
                    clickCount = 0
                    label.text = "Draw triangle. Click first point!"
                }
            } else if (mode == MODE_LINE) {
                if (clickCount == 0) {
                    x1 = e.getX()
                    y1 = e.getY()
                    clickCount++
                    label.text = "Draw line. Click second point!"
                } else {
                    graphicsContext.beginPath()
                    graphicsContext.moveTo(e.getX(), e.getY())
                    graphicsContext.lineTo(x1, y1)
                    graphicsContext.stroke()
                    graphicsContext.closePath()
                    clickCount = 0
                    label.text = "Draw line. Click first point!"
                }
            } else if (mode == MODE_CIRCLE) {
                if (clickCount == 0) {
                    x1 = e.getX()
                    y1 = e.getY()
                    clickCount++
                    label.text = "Draw circle. Click point on circle!"
                } else {
                    val r = sqrt((e.getX()-x1)*(e.getX()-x1)+(e.getY()-y1)*(e.getY()-y1))
                    graphicsContext.strokeOval(x1-r,y1-r,r*2,r*2)
                    clickCount = 0
                    label.text = "Draw circle. Click center point!"
                }
            } else {
                graphicsContext.lineTo(e.getX(), e.getY())
                graphicsContext.stroke()
                graphicsContext.closePath()
            }
        }
        val mousePressed = EventHandler { e: MouseEvent ->
            graphicsContext.beginPath()
            graphicsContext.moveTo(e.getX(), e.getY())
        }
        val mouseDragged = EventHandler { e: MouseEvent ->
            graphicsContext.lineTo(e.getX(), e.getY())
            graphicsContext.stroke()
            graphicsContext.closePath()
            graphicsContext.beginPath()
            graphicsContext.moveTo(e.getX(), e.getY())
        }

        val mouseReleased = EventHandler { e: MouseEvent ->
            graphicsContext.lineTo(e.getX(), e.getY())
            graphicsContext.stroke()
            graphicsContext.closePath()
        }

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClicked);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressed);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleased);
        stage.title = "Simple graphics editor"
        stage.show()
    }

    private companion object {
        const val MODE_LINE = 2
        const val MODE_TRIANGLE = 3
        const val MODE_CIRCLE = 1
        const val MODE_RECTANGLE = 4
        const val MODE_PENCIL = 0
    }
}

fun main() {
    Application.launch(SGEApplication::class.java)
}