package com.kc.lesson2

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ColorPicker
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import javafx.stage.Stage
import java.io.File
import kotlin.math.abs
import kotlin.math.sqrt


class SGEApplication : Application() {
    override fun start(stage: Stage) {
        val shape = ArrayList<Shape>()
        val buttonDrag = Button("Drag")
        val buttonRectangle = Button("Rectangle")
        val buttonTriangle = Button("Triangle")
        val buttonLine = Button("Line")
        val buttonCircle = Button("Circle")
        val buttonStar = Button("Star")
        val colorPicker = ColorPicker()
        val group = Group()
        val buttonSave = Button("Save")
        val buttonLoad = Button("Load")
        val buttonBack = Button("<-")
        val lineWidth = WIDTH_DEFAULT
        colorPicker.value = Color.BLUE
        var str = ""
        var x0 = 0.0
        var y0 = 0.0
        var xs0 = 0.0
        var ys0 = 0.0
        var xe0 = 0.0
        var ye0 = 0.0
        var indexDragShape = 0
        var mode = MODE_DRAG
        val hBox = HBox()

        hBox.children.addAll(
            buttonDrag,
            buttonRectangle,
            buttonTriangle,
            buttonLine,
            buttonCircle,
            buttonStar,
            colorPicker,
            buttonSave,
            buttonLoad,
            buttonBack,
        )
        group.children.addAll(hBox)

        stage.scene = Scene(group, SCENE_XMAX, SCENE_YMAX)

        buttonBack.onAction = EventHandler {
            if (shape.isNotEmpty()) {
                shape.removeLast()
                group.children.removeLastOrNull()
            }
        }

        buttonDrag.onAction = EventHandler { mode = MODE_DRAG }
        buttonRectangle.onAction = EventHandler { mode = MODE_RECTANGLE }
        buttonTriangle.onAction = EventHandler { mode = MODE_TRIANGLE }
        buttonLine.onAction = EventHandler { mode = MODE_LINE }
        buttonCircle.onAction = EventHandler { mode = MODE_CIRCLE }
        buttonStar.onAction = EventHandler { mode = MODE_STAR }

        buttonSave.onAction = EventHandler {
            File(FILE_NAME).printWriter().use { out -> shape.forEach { out.println(it) } }
        }

        buttonLoad.onAction = EventHandler {

            fun nextParam(): String {
                val res: String
                str = str.substringAfter("=")
                if ("," in str) {
                    res = str.substringBefore(",")
                    str = str.substringAfter(",")
                } else {
                    res = str.substringBefore("]")
                    str = ""
                }
                return res
            }
/*
            fun nextParamName(): String {
                var res = str.substringBefore("=").trimStart()
                str = str.substringAfter("=")
                return res
            }
*/
            val file = File(FILE_NAME)
            val bufferedReader = file.bufferedReader()
            val text = bufferedReader.readLines()
            shape.clear()
            for (line in text) {
                str = line
                val sh = line.substringBefore("[")
                str = line.substringAfter("[")
                when (sh) {
                    "Star" -> {
                        shape.add(Star())
                        (shape.last() as Star).x = nextParam().toDouble()
                        (shape.last() as Star).y = nextParam().toDouble()
                        (shape.last() as Star).width = nextParam().toDouble()
                        (shape.last() as Star).height = nextParam().toDouble()
                        (shape.last() as Star).stroke = Paint.valueOf(nextParam())
                        (shape.last() as Star).strokeWidth = nextParam().toDouble()
                        (shape.last() as Star).repaint()
                    }

                    "Rectangle" -> {
                        shape.add(Rectangle())
                        (shape.last() as Rectangle).x = nextParam().toDouble()
                        (shape.last() as Rectangle).y = nextParam().toDouble()
                        (shape.last() as Rectangle).width = nextParam().toDouble()
                        (shape.last() as Rectangle).height = nextParam().toDouble()
                        (shape.last() as Rectangle).fill = Paint.valueOf(nextParam())
                        (shape.last() as Rectangle).stroke = Paint.valueOf(nextParam())
                        (shape.last() as Rectangle).strokeWidth = nextParam().toDouble()
                    }

                    "Circle" -> {
                        shape.add(Circle())
                        (shape.last() as Circle).centerX = nextParam().toDouble()
                        (shape.last() as Circle).centerY = nextParam().toDouble()
                        (shape.last() as Circle).radius = nextParam().toDouble()
                        (shape.last() as Circle).fill = Paint.valueOf(nextParam())
                        (shape.last() as Circle).stroke = Paint.valueOf(nextParam())
                        (shape.last() as Circle).strokeWidth = nextParam().toDouble()
                    }

                    "Line" -> {
                        shape.add(Line())
                        (shape.last() as Line).startX = nextParam().toDouble()
                        (shape.last() as Line).startY = nextParam().toDouble()
                        (shape.last() as Line).endX = nextParam().toDouble()
                        (shape.last() as Line).endY = nextParam().toDouble()
                        (shape.last() as Line).stroke = Paint.valueOf(nextParam())
                        (shape.last() as Line).strokeWidth = nextParam().toDouble()
                    }

                    "Triangle" -> {
                        shape.add(Triangle())
                        (shape.last() as Triangle).x = nextParam().toDouble()
                        (shape.last() as Triangle).y = nextParam().toDouble()
                        (shape.last() as Triangle).width = nextParam().toDouble()
                        (shape.last() as Triangle).height = nextParam().toDouble()
                        (shape.last() as Triangle).stroke = Paint.valueOf(nextParam())
                        (shape.last() as Triangle).strokeWidth = nextParam().toDouble()
                        (shape.last() as Triangle).repaint()
                    }
                }
            }
            group.children.clear()
            group.children.add(hBox)
            for (sh in shape) group.children.add(sh)
        }

        val mousePressed = EventHandler { e: MouseEvent ->
            x0 = e.x
            y0 = e.y
            when (mode) {
                MODE_DRAG -> {
                    indexDragShape = -1
                    for (i in shape.size - 1 downTo 0) {
                        if (shape[i].contains(x0, y0)) {
                            indexDragShape = i
                            break
                        }
                    }
                    if (indexDragShape != -1) {
                        when (shape[indexDragShape]) {
                            is Rectangle -> {
                                xs0 = (shape[indexDragShape] as Rectangle).x
                                ys0 = (shape[indexDragShape] as Rectangle).y
                            }

                            is Line -> {
                                xs0 = (shape[indexDragShape] as Line).startX
                                ys0 = (shape[indexDragShape] as Line).startY
                                xe0 = (shape[indexDragShape] as Line).endX
                                ye0 = (shape[indexDragShape] as Line).endY
                            }

                            is Triangle -> {
                                xs0 = (shape[indexDragShape] as Triangle).x
                                ys0 = (shape[indexDragShape] as Triangle).y
                            }

                            is Star -> {
                                xs0 = (shape[indexDragShape] as Star).x
                                ys0 = (shape[indexDragShape] as Star).y
                            }

                            is Circle -> {
                                xs0 = (shape[indexDragShape] as Circle).centerX
                                ys0 = (shape[indexDragShape] as Circle).centerY
                            }
                        }
                    }
                }

                MODE_RECTANGLE -> {
                    shape.add(Rectangle(e.x, e.y, 5.0, 5.0))
                    (shape.last() as Rectangle).stroke = colorPicker.value
                    (shape.last() as Rectangle).fill = Color.TRANSPARENT
                    (shape.last() as Rectangle).strokeWidth = lineWidth
                    group.children.add(shape.last())
                }

                MODE_STAR -> {
                    shape.add(Star(e.x, e.y, 5.0, 5.0, colorPicker.value, lineWidth))
                    group.children.add(shape.last())
                }

                MODE_TRIANGLE -> {
                    shape.add(Triangle(e.x, e.y, 5.0, 5.0, colorPicker.value, lineWidth))
                    group.children.add(shape.last())
                }

                MODE_LINE -> {
                    shape.add(Line(e.x, e.y, e.x + 1, e.y + 1))
                    (shape.last() as Line).stroke = colorPicker.value
                    (shape.last() as Line).strokeWidth = lineWidth
                    group.children.add(shape.last())
                }

                MODE_CIRCLE -> {
                    shape.add(Circle(e.x, e.y, 1.0, Color.TRANSPARENT))
                    (shape.last() as Circle).stroke = colorPicker.value
                    (shape.last() as Circle).strokeWidth = lineWidth
                    group.children.add(shape.last())
                }
            }
        }

        fun drawSegment(e: MouseEvent) {
            when (shape.last()) {
                is Rectangle -> {
                    if ((shape.last() as Rectangle).x > e.x) (shape.last() as Rectangle).x = e.x
                    (shape.last() as Rectangle).width = abs(e.x - x0)
                    if ((shape.last() as Rectangle).y > e.y) (shape.last() as Rectangle).y = e.y
                    (shape.last() as Rectangle).height = abs(e.y - y0)
                }

                is Star -> {
                    if ((shape.last() as Star).x > e.x) (shape.last() as Star).x = e.x
                    (shape.last() as Star).width = abs(e.x - x0)
                    if ((shape.last() as Star).y > e.y) (shape.last() as Star).y = e.y
                    (shape.last() as Star).height = abs(e.y - y0)
                    (shape.last() as Star).repaint()
                }

                is Triangle -> {
                    if ((shape.last() as Triangle).x > e.x) (shape.last() as Triangle).x = e.x
                    (shape.last() as Triangle).width = abs(e.x - x0)
                    if ((shape.last() as Triangle).y > e.y) (shape.last() as Triangle).y = e.y
                    (shape.last() as Triangle).height = abs(e.y - y0)
                    (shape.last() as Triangle).repaint()
                }

                is Line -> {
                    (shape.last() as Line).endX = e.x
                    (shape.last() as Line).endY = e.y
                }

                is Circle -> {
                    (shape.last() as Circle).radius = sqrt((e.x - x0) * (e.x - x0) + (e.y - y0) * (e.y - y0))
                }
            }
        }

        val mouseDragged = EventHandler { e: MouseEvent ->
            if (mode == MODE_DRAG) {
                if (indexDragShape != -1) {
                    when (shape[indexDragShape]) {
                        is Rectangle -> {
                            (shape[indexDragShape] as Rectangle).x = xs0 + e.x - x0
                            (shape[indexDragShape] as Rectangle).y = ys0 + e.y - y0
                        }

                        is Line -> {
                            (shape[indexDragShape] as Line).startX = xs0 + e.x - x0
                            (shape[indexDragShape] as Line).startY = ys0 + e.y - y0
                            (shape[indexDragShape] as Line).endX = xe0 + e.x - x0
                            (shape[indexDragShape] as Line).endY = ye0 + e.y - y0
                        }

                        is Triangle -> {
                            (shape[indexDragShape] as Triangle).x = xs0 + e.x - x0
                            (shape[indexDragShape] as Triangle).y = ys0 + e.y - y0
                            (shape[indexDragShape] as Triangle).repaint()
                        }

                        is Star -> {
                            (shape[indexDragShape] as Star).x = xs0 + e.x - x0
                            (shape[indexDragShape] as Star).y = ys0 + e.y - y0
                            (shape[indexDragShape] as Star).repaint()
                        }

                        is Circle -> {
                            (shape[indexDragShape] as Circle).centerX = xs0 + e.x - x0
                            (shape[indexDragShape] as Circle).centerY = ys0 + e.y - y0
                        }
                    }
                }
            } else {
                drawSegment(e)
            }
        }
        val mouseReleased = EventHandler { e: MouseEvent -> if (mode != MODE_DRAG) drawSegment(e) }

        stage.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressed);
        stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragged);
        stage.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleased);
        stage.title = "Simple graphics editor"
        stage.show()
    }

    private companion object {
        const val MODE_DRAG = 0
        const val MODE_CIRCLE = 1
        const val MODE_LINE = 2
        const val MODE_TRIANGLE = 3
        const val MODE_RECTANGLE = 4
        const val MODE_STAR = 5
        const val WIDTH_DEFAULT = 2.5
        const val FILE_NAME = "sge.json"
        const val SCENE_XMAX = 600.0
        const val SCENE_YMAX = 500.0
    }
}

fun main() {
    Application.launch(SGEApplication::class.java)
}