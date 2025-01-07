package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.JavaView
import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.paint.Color
import javax.inject.Singleton

@Singleton
class ToolBarViewModel : ViewModel {
    private val btnCurrentFgColor = SimpleObjectProperty<Color>(Color.RED)
    private val btnCurrentBgColor = SimpleObjectProperty<Color>()

    fun getBbnCurrentFgColor() = btnCurrentFgColor.get()
    fun setBtnCurrentFgColor(color: Color) = btnCurrentFgColor.set(color)
    fun btnCurrentColorFgProperty() = btnCurrentFgColor

    fun getBbnCurrentBgColor() = btnCurrentBgColor.get()
    fun setBtnCurrentBgColor(color: Color) = btnCurrentBgColor.set(color)
    fun btnCurrentColorBgProperty() = btnCurrentBgColor
}