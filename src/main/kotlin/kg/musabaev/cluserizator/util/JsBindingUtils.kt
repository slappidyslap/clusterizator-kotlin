package kg.musabaev.cluserizator.util

import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import netscape.javascript.JSObject

class JsBindings {
    companion object {
        fun <T> bindContent(list: ObservableList<T>, jsList: JSObject) {
            list.addListener { change: ListChangeListener.Change<out T> ->
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (item in change.addedSubList) {
                            jsList.call("push", item)
                        }
                    }
                    if (change.wasRemoved()) {
                        for (item in change.removed) {
                            jsList.call("splice", jsList.call("indexOf", item), 1)
                        }
                    }
                }
            }
        }
    }
}