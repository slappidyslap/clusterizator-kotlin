package kg.musabaev.cluserizator

import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox

class TreeCellGraphic(id: Int, input: String, freq: String) : HBox() {
    val idNode: Label = Label(id.toString())
    val input: TextField = TextField(input)
    val freq: Label = Label(freq)

    init {
        super.getChildren().add(this.idNode)
        super.getChildren().add(this.input)
        super.getChildren().add(this.freq)
    }
}