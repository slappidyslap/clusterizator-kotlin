package kg.musabaev.cluserizator.menu

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONReader.Feature.AllowUnQuotedFieldNames
import com.alibaba.fastjson2.JSONWriter.Feature.UnquoteFieldName
import javafx.collections.FXCollections.observableArrayList
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType.CONFIRMATION
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.domain.GraphClusters
import kg.musabaev.cluserizator.domain.GraphClusterItem
import kg.musabaev.cluserizator.domain.SeoKeyword
import kg.musabaev.cluserizator.file.CsvHandler
import java.io.*
import java.net.URL
import java.nio.file.Files
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
private class SaveLoadTestMenuView() : MenuView(), Initializable {

    private lateinit var graphClusters: GraphClusters
    private lateinit var menuViewModel: MenuViewModel

    @Inject
    constructor(graphClusters: GraphClusters, menuViewModel: MenuViewModel) : this() {
        this.graphClusters = graphClusters
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
                    graphClusters["root"] = GraphClusterItem(
                        id = "root",
                        seoKeywords = observableArrayList(a))
                }},
                MenuItem("Удалить все кластеры").apply { setOnAction { TODO() } }
            )
        }) // TODO мб потом перенести в initialize
    }

    override fun loadProject() {
        menuViewModel.setIsLoadingFromSave(true)
        BufferedInputStream(FileInputStream("test.seoclztr")).use { input ->
            val loadedGraphClusters = JSON.parseObject<GraphClusters>( // TODO оптимизировать
                input,
                GraphClusters::class.java)
            graphClusters.getKeywordContext().clear()
            graphClusters.getKeywordContext().addAll(loadedGraphClusters.getKeywordContext())
            graphClusters.getMap().clear()
            graphClusters.getMap().putAll(loadedGraphClusters.getMap())
        }
        menuViewModel.setIsLoadingFromSave(false)
    }

    override fun saveProject() {
        // TODO() тут надо изучить какой размера буфера можно выделить
        BufferedOutputStream(FileOutputStream("test.seoclztr"), 128).use { output ->
            JSON.writeTo(
                output,
                graphClusters
            )
        }
    }

    override fun importCsv() {
        val seoKeywords = mutableListOf<SeoKeyword>()
        CsvHandler("proseller.csv")
            .linesAsSequence()
            .forEachIndexed { i, lines ->
                if (i == 0) {
                    graphClusters.getKeywordContext().clear()
                    graphClusters.getKeywordContext().addAll(lines)
                } else {
                    val keyword = lines[0]
                    val otherMetas = lines.subList(1, lines.lastIndex)
                    seoKeywords.add(SeoKeyword(keyword, otherMetas))
                }
            }
        val result = Alert(CONFIRMATION).apply {
            title = "Окно подтверждения"
            headerText = "После импортирования, текущий процесс будет утерян. Вы правда хотите импортировать?"
        }.showAndWait()
        if (result.get() == ButtonType.OK) {
            graphClusters.clear()
            graphClusters["root"] = GraphClusterItem("root", seoKeywords)
        }
    }

    override fun exportProject() {
        val root: GraphClusterItem = graphClusters["root"]!!
        val csvHeader = graphClusters.getKeywordContext().joinToString(separator = ",")
        File("testresult.csv").bufferedWriter().use { writer ->
            writer.write("Group name,$csvHeader\n")

            fun writeItem(key: String, item: GraphClusterItem, visited: MutableSet<String>) {
                if (key in visited) return
                visited.add(key)

                val seoKeywords = item.seoKeywords()
                val neighbors = item.neighbors()

                if (seoKeywords.isEmpty()) {
                    writer.write("$key,,\n")
                } else {
                    seoKeywords.forEach { keyword ->
                        val metas = keyword.otherMetas().joinToString(separator = ",")
                        writer.write("$key,${keyword.getKeyword()},$metas\n")
                    }
                }

                neighbors.forEach { neighbor ->
                    writeItem(neighbor.getId(), neighbor, visited)
                }
            }

            val visited = mutableSetOf<String>()
            writeItem("root", root, visited)
        }

    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
//        TODO() срабатывает
    }
}