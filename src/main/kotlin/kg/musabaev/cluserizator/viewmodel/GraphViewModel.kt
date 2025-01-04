package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleStringProperty
import javax.inject.Singleton

@Singleton
class GraphViewModel : ViewModel {
    val selectedGraphId = SimpleStringProperty()
}