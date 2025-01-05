package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.beans.binding.Bindings
import javafx.collections.ObservableList
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.BorderPane
import kg.musabaev.cluserizator.viewmodel.*
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeoKeywordTableView() : BorderPane(), Initializable, JavaView<SeoKeywordTableViewModel> {

    private val table: TableView<SeoKeywordModel> = TableView()

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
        println("initTableView0")
        val idColumn = TableColumn<SeoKeywordModel, String>("ID").apply {
            cellValueFactory = PropertyValueFactory("id")
        }

        val keyColumn = TableColumn<SeoKeywordModel, String>("Ключевое слово").apply {
            cellValueFactory = PropertyValueFactory("keyword")
        }

        table.columns.addAll(idColumn, keyColumn)
        super.setCenter(table)
        Bindings.bindContentBidirectional(table.items, keywordTableViewModel.keywords)
//        println("hello")
//        println(clusterMapModel.clusterMap.toString())
//        println("До вставки ${table.items}")
//        keywordTableViewModel.keywords.setAll(clusterMapModel.clusterMap["1"])
//        println("После вставки ${table.items}")

    }

    private fun initListeners() {
        // TODO проверить что newVal не null
        table.selectionModelProperty()
        graphViewModel.selectedGraphIdProperty().addListener { _, _, newVal ->
            graphClusterMap.map[newVal].let {
                keywordTableViewModel.keywords.setAll(it!!.seoKeywords())
            }
        }
    }
}