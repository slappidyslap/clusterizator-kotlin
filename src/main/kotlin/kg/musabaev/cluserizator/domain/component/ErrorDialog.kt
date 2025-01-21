package kg.musabaev.cluserizator.domain.component

import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import java.io.PrintWriter
import java.io.StringWriter

class ErrorDialog(exception: Throwable) : Alert(AlertType.ERROR) {

    init {
        title = "Окно стектрейса"
        headerText = "Произошла ошибка"

        val label = Label("Стектрейс исключения:")

        val stackTrace = getStackTrace(exception)

        val textArea = TextArea(stackTrace).apply {
            isEditable = false
            isWrapText = false
            maxWidth = Double.MAX_VALUE
            maxHeight = Double.MAX_VALUE
        }

        GridPane.setHgrow(textArea, Priority.ALWAYS)
        GridPane.setVgrow(textArea, Priority.ALWAYS)

        val content = GridPane().apply {
            maxWidth = Double.MAX_VALUE
            add(label, 0, 0)
            add(textArea, 0, 1)
        }

        dialogPane.content = content
    }

    private fun getStackTrace(exception: Throwable): String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        exception.printStackTrace(printWriter)
        return stringWriter.toString()
    }
}