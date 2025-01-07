package kg.musabaev.cluserizator.util

import javafx.scene.Node

// Добавляет стиль, а если он существует, то перезаписывает значение
fun Node.setStyle(property: String, value: String) {
    val currentStyle = this.style
    val regex = Regex("\\b$property: [^;]*;")
    this.style = if (regex.containsMatchIn(currentStyle)) {
        currentStyle.replace(regex, "$property: $value;")
    } else {
        if (currentStyle.isNotEmpty() && !currentStyle.endsWith(";")) {
            "$currentStyle; $property: $value;"
        } else {
            "$currentStyle$property: $value;"
        }
    }
}

// Удаляет указанное свойство, если оно существует
fun Node.removeStyle(property: String) {
    val currentStyle = this.style
    val regex = Regex("\\b$property: [^;]*;")
    this.style = currentStyle.replace(regex, "").trim().removeSuffix(";")
}