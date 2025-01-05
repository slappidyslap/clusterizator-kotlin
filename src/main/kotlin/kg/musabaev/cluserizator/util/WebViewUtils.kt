package kg.musabaev.cluserizator.util

import javafx.concurrent.Worker
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView

fun WebView.executeScriptLater(script: String): Any? {
    var any: Any? = null
    this.engine.loadWorker.stateProperty().addListener {_, _, newVal ->
        if (newVal == Worker.State.SUCCEEDED) {
            any = this.engine.executeScript(script)
        }
    }
    return any
}

fun WebEngine.executeScriptSafely(script: String): Any? {
    try {
        return this.executeScript(script)
    } catch (e: Exception) {
        if (e.message!!.contains("item with id 22 already exists")) return null
        e.printStackTrace()
        return null
    }
}