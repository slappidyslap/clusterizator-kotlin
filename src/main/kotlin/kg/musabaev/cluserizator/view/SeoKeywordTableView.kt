package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.beans.binding.Bindings
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.BorderPane
import kg.musabaev.cluserizator.domain.AutoIncrementTableCellFactory
import kg.musabaev.cluserizator.domain.GraphClusters
import kg.musabaev.cluserizator.domain.SeoKeyword
import kg.musabaev.cluserizator.viewmodel.GraphViewModel
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
    private lateinit var graphClusters: GraphClusters

    @Inject
    constructor(keywordTableViewModel: SeoKeywordTableViewModel, graphViewModel: GraphViewModel, graphClusters: GraphClusters) : this() {
        this.keywordTableViewModel = keywordTableViewModel
        this.graphViewModel = graphViewModel
        this.graphClusters = graphClusters
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        initTableView()
        initListeners()
    }

    private fun initTableView() {
        table.isEditable = true
        val idColumn = TableColumn<SeoKeyword, String>("ID").apply {
            cellFactory = AutoIncrementTableCellFactory<SeoKeyword, String>()
            isSortable = false
            isEditable = false
        }

        val keywordColumn = TableColumn<SeoKeyword, String>("Ключевое слово").apply {
            cellValueFactory = PropertyValueFactory("keyword")
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { e ->
                (e.tableView
                    .items[e.tablePosition.row] as SeoKeyword)
                    .setKeyword(e.newValue)
            }
            prefWidthProperty().bind(
                Bindings.createDoubleBinding(
                    { table.width - idColumn.width - 2 }, // -2 для небольшого отступа
                    table.widthProperty(),
                    idColumn.widthProperty()
                )
            )
        }

        table.columns.addAll(idColumn, keywordColumn)
        super.setCenter(table)
        Bindings.bindContentBidirectional(table.items, keywordTableViewModel.keywords)
    }

    private fun initListeners() {
        graphViewModel.selectedClusterIdProperty().addListener { _, _, newVal ->
            if (newVal == null || newVal.isEmpty()) {
                keywordTableViewModel.keywords.clear()  // TODO может вместо clear есть другой способ
            } else {
                graphClusters[newVal].let {
                    keywordTableViewModel.keywords.setAll(it!!.seoKeywords()) // TODO может вместо setAll есть другой способ
                }
            }
        }
    }
}