package kg.musabaev.cluserizator

import de.saxsys.mvvmfx.ViewModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javax.inject.Singleton

@Singleton
class MainViewModel : ViewModel {
    val seoKeywords: ObservableList<SeoKeywordModel> = FXCollections.observableArrayList()
}