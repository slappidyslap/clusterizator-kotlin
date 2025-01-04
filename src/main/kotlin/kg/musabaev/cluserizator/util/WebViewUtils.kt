package kg.musabaev.cluserizator.util

import javafx.concurrent.Worker
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