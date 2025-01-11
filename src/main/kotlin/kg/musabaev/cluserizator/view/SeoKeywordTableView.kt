package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.beans.binding.Bindings
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.BorderPane
import kg.musabaev.cluserizator.viewmodel.GraphClusterMap
import kg.musabaev.cluserizator.viewmodel.GraphViewModel
import kg.musabaev.cluserizator.viewmodel.SeoKeyword
import kg.musabaev.cluserizator.viewmodel.SeoKeywordTableViewModel
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeoKeywordTableView() : BorderPane(), Initializable, JavaView<SeoKeywordTableViewModel> {

    private val table: TableView<SeoKeyword> = TableView()

    @InjectViewModel
    private lateinit var keywordTableViewModel: SeoKeywordTableViewModel
    private lateinit var graphViewModel: GraphViewModel
    private lateinit var graphClusterMap: GraphClusterMap

    @Inject
    constructor(keywordTableViewModel: SeoKeywordTableViewModel, graphViewModel: GraphViewModel, graphClusterMap: GraphClusterMap) : this() {
        this.keywordTableViewModel = keywordTableViewModel
        this.graphViewModel = graphViewModel
        this.graphClusterMap = graphClusterMap
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        initTableView()
        initListeners()
    }

    private fun initTableView() {
        val idColumn = TableColumn<SeoKeyword, String>("ID").apply {
//            cellValueFactory = PropertyValueFactory("id")
        }

        val keyColumn = TableColumn<SeoKeyword, String>("Ключевое слово").apply {
            cellValueFactory = PropertyValueFactory("keyword")
        }

        table.columns.addAll(idColumn, keyColumn)
        super.setCenter(table)
        Bindings.bindContentBidirectional(table.items, keywordTableViewModel.keywords)
    }

    private fun initListeners() {
        graphViewModel.selectedClusterIdProperty().addListener { _, _, newVal ->
            if (newVal == null || newVal.isEmpty()) {
                keywordTableViewModel.keywords.clear()  // TODO может вместо clear есть другой способ
            } else {
                graphClusterMap.map[newVal].let {
                    keywordTableViewModel.keywords.setAll(it!!.seoKeywords()) // TODO может вместо setAll есть другой способ
                }
            }
        }
    }
}