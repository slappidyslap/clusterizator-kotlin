package kg.musabaev.cluserizator.graph

import de.saxsys.mvvmfx.JavaView
import javafx.fxml.Initializable
import javafx.scene.control.TableView
import javafx.scene.layout.BorderPane
import kg.musabaev.cluserizator.SeoKeywordModel
import kg.musabaev.cluserizator.SeoKeywordTableViewModel
import java.net.URL
import java.util.*

class SeoKeywordTableView : BorderPane, Initializable, JavaView<SeoKeywordTableViewModel> {

    private val keywordTable: TableView<SeoKeywordModel>

    constructor() : super() {
        keywordTable = TableView()
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        TODO("Not yet implemented")
    }

}