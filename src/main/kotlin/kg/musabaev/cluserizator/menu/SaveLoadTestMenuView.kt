package kg.musabaev.cluserizator.menu

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONReader.Feature.AllowUnQuotedFieldNames
import com.alibaba.fastjson2.JSONWriter.Feature.PrettyFormat
import com.alibaba.fastjson2.JSONWriter.Feature.UnquoteFieldName
import javafx.collections.FXCollections.observableArrayList
import javafx.fxml.Initializable
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.viewmodel.GraphClusterMap
import kg.musabaev.cluserizator.viewmodel.GraphClusterValue
import kg.musabaev.cluserizator.viewmodel.SeoKeyword
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveLoadTestMenuView() : MenuView(), Initializable {

    private lateinit var graphClusterMap: GraphClusterMap
    private lateinit var menuViewModel: MenuViewModel

    @Inject
    constructor(graphClusterMap: GraphClusterMap, menuViewModel: MenuViewModel) : this() {
        this.graphClusterMap = graphClusterMap
        this.menuViewModel = menuViewModel

        super.getMenus().add(Menu("Для разработчиков").apply {
            items.addAll(
                MenuItem("Пример графа").apply { setOnAction {
                    val a = observableArrayList<SeoKeyword>()
                    TestCsvFileHandler().getLinesCsv().forEach { line ->
                        val values = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toList()
                        val otherMetas = values.subList(1, values.lastIndex - 1)
                        val keyword = values[0]

                        val seoKeyword = SeoKeyword(keyword, otherMetas)
                        a.add(seoKeyword)
                    }
                    graphClusterMap.map["root"] = GraphClusterValue(
                        clusterId = "root",
                        seoKeywords = observableArrayList(a))
                }},
                MenuItem("Удалить все кластеры").apply { setOnAction { TODO() } }
            )
        }) // TODO мб потом перенести в initialize
    }

    override fun loadFile() {
        menuViewModel.setIsLoadingFromSave(true)
        BufferedInputStream(FileInputStream("test.seoclztr")).use { input ->
            val root = JSON.parseObject<GraphClusterValue>(
                input,
                GraphClusterValue::class.java,
                AllowUnQuotedFieldNames)
            putClustersRecursively(root)
            graphClusterMap.map["root"] = root
        }
        menuViewModel.setIsLoadingFromSave(false)
    }

    override fun saveFile() {
        // TODO() тут надо изучить какой размера буфера можно выделить
        BufferedOutputStream(FileOutputStream("test.seoclztr"), 128).use { output ->
            JSON.writeTo(
                output,
                graphClusterMap.map["root"],
                UnquoteFieldName
            )
        }
    }

    private fun putClustersRecursively(cluster: GraphClusterValue) {
        if (cluster.neighborClusters().isEmpty()) return
        for (neighbor in cluster.neighborClusters()) {
            putClustersRecursively(neighbor)
            graphClusterMap.map[neighbor.getClusterId()] = neighbor
        }
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
//        TODO() срабатывает
    }
}