package kg.musabaev.cluserizator.graph

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.BorderPane
import kg.musabaev.cluserizator.SeoKeywordModel
import kg.musabaev.cluserizator.SeoKeywordTableViewModel
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeoKeywordTableView() : BorderPane(), Initializable, JavaView<SeoKeywordTableViewModel> {

    private val keywordTable: TableView<SeoKeywordModel> = TableView()

    @InjectViewModel
    private lateinit var keywordTableViewModel: SeoKeywordTableViewModel

    @Inject
    constructor(keywordTableViewModel: SeoKeywordTableViewModel) : this() {
        this.keywordTableViewModel = keywordTableViewModel
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        val idColumn: TableColumn<SeoKeywordModel, Int> = TableColumn("ID")
        idColumn.cellValueFactory = PropertyValueFactory("id")

        val keyColumn: TableColumn<SeoKeywordModel, String> = TableColumn("Ключевое слово")
        keyColumn.cellValueFactory = PropertyValueFactory("keyword")

        keywordTable.columns.addAll(idColumn, keyColumn)

        keywordTable.items = keywordTableViewModel.seoKeywords
        super.setCenter(keywordTable)
    }

}