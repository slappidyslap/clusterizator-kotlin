package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableObjectValue
import javax.inject.Singleton

@Singleton
class GraphViewModel : ViewModel {
    private val selectedGraphIdProperty = SimpleStringProperty()

    fun getSelectedGraphId() = selectedGraphIdProperty.get()
    fun setSelectedGraphId(graphId: String) = selectedGraphIdProperty.set(graphId)
    fun selectedGraphIdProperty() = selectedGraphIdProperty
}