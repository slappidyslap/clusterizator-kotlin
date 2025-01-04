package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.BorderPane
import javafx.util.Callback
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.viewmodel.GraphViewModel
import kg.musabaev.cluserizator.viewmodel.SeoClusterMapModel
import kg.musabaev.cluserizator.viewmodel.SeoKeywordModel
import kg.musabaev.cluserizator.viewmodel.SeoKeywordTableViewModel
import java.net.URL
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeoKeywordTableView() : BorderPane(), Initializable, JavaView<SeoKeywordTableViewModel> {

    private val table: TableView<SeoKeywordModel> = TableView()

    @InjectViewModel
    private lateinit var keywordTableViewModel: SeoKeywordTableViewModel
    private lateinit var graphViewModel: GraphViewModel
    private lateinit var clusterMapModel: SeoClusterMapModel

    @Inject
    constructor(keywordTableViewModel: SeoKeywordTableViewModel, graphViewModel: GraphViewModel, clusterMapModel: SeoClusterMapModel) : this() {
        this.keywordTableViewModel = keywordTableViewModel
        this.graphViewModel = graphViewModel
        this.clusterMapModel = clusterMapModel
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


        // TODO
        fillTestCluster()
//        println("hello")
//        println(clusterMapModel.clusterMap.toString())
//        println("До вставки ${table.items}")
//        keywordTableViewModel.keywords.setAll(clusterMapModel.clusterMap["1"])
//        println("После вставки ${table.items}")

    }

    private fun initListeners() {
        // TODO проверить что newVal не null

        graphViewModel.selectedGraphId.addListener { _, _, newVal ->
            println(clusterMapModel.clusterMap[newVal])
            clusterMapModel.clusterMap[newVal].let {
                keywordTableViewModel.keywords.setAll(it)
                println(keywordTableViewModel.keywords.map { it.getKeyword() })
            }
        }
    }

    private fun fillTestCluster() {
        val i = AtomicReference(1)
        val a = FXCollections.observableArrayList<SeoKeywordModel>()
        TestCsvFileHandler().getLinesCsv().forEach { line ->
            println("initTableView$i")
            val values = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val otherMetas = values.copyOfRange(1, values.lastIndex - 1)
            val id = i.get()
            val keyword = values[0]

            val seoKeyword = SeoKeywordModel(id.toString(), keyword, otherMetas)
            i.getAndSet(i.get() + 1)
            a.add(seoKeyword)
        }
        println(keywordTableViewModel.keywords.map { it.getKeyword() }.size)
        clusterMapModel.clusterMap["1"] = FXCollections.observableArrayList(a.subList(0, a.size / 2))
        clusterMapModel.clusterMap["2"] = FXCollections.observableArrayList(a.subList(a.size / 2, a.size))
    }
}