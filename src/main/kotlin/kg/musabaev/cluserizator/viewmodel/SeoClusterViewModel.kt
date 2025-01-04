package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javax.inject.Singleton

@Singleton
class SeoClusterViewModel : ViewModel {
    val node = SimpleStringProperty()
    val edges = FXCollections.observableArrayList<String>()
    val seoKeywords = FXCollections.observableArrayList<String>()
}