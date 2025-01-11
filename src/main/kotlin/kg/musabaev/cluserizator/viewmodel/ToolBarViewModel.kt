package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.paint.Color
import javax.inject.Singleton

@Singleton
class ToolBarViewModel : ViewModel {
    private val btnsFgColor = SimpleObjectProperty(Color.WHITE) // TODO надо менять по тому какой сейчас режим
    private val btnsBgColor = SimpleObjectProperty(Color.BLACK)

    fun getBtnsFgColor() = btnsFgColor.get()
    fun setBtnsFgColor(color: Color) = btnsFgColor.set(color)
    fun btnsColorFgProperty() = btnsFgColor

    fun getBtnsBgColor() = btnsBgColor.get()
    fun setBtnsBgColor(color: Color) = btnsBgColor.set(color)
    fun btnsColorBgProperty() = btnsBgColor
}