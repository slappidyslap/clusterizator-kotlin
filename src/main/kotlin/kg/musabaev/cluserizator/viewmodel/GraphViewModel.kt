package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleStringProperty
import javax.inject.Singleton

@Singleton
class GraphViewModel : ViewModel {
    private val selectedClusterIdProperty = SimpleStringProperty()

    fun getSelectedClusterId() = selectedClusterIdProperty.get()
    fun setSelectedClusterId(id: String) = selectedClusterIdProperty.set(id)
    fun selectedClusterIdProperty() = selectedClusterIdProperty
}