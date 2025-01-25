package kg.musabaev.cluserizator.menu

import com.alibaba.fastjson2.JSON
import javafx.collections.FXCollections.observableArrayList
import javafx.fxml.Initializable
import javafx.scene.control.ButtonType
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import kg.musabaev.cluserizator.domain.GraphClusterItem
import kg.musabaev.cluserizator.domain.GraphClusters
import kg.musabaev.cluserizator.domain.SeoKeyword
import kg.musabaev.cluserizator.domain.component.NewProjectConfirmationDialog
import kg.musabaev.cluserizator.domain.component.NewProjectConfirmationDialog.Type
import kg.musabaev.cluserizator.file.CsvHandler
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import java.io.*
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimpleMenuView() : MenuView(), Initializable {

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
            )
        }) // TODO мб потом перенести в initialize
    }

    override fun loadProject() {
        menuViewModel.setIsLoadingFromSave(true)

        areThereNoClustersOrElseDialog(Type.ON_LOAD) ?: return
        val seoclztr = chooseSeoclztr(ChooseFileTo.OPEN) ?: return

        BufferedInputStream(FileInputStream(seoclztr)).use { input ->
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
        val seoclztr = chooseSeoclztr(ChooseFileTo.SAVE) ?: return

        // TODO() тут надо изучить какой размера буфера можно выделить
        BufferedOutputStream(FileOutputStream(seoclztr), 128).use { output ->
            JSON.writeTo(
                output,
                graphClusters
            )
        }
    }

    override fun importCsv() {
        areThereNoClustersOrElseDialog(Type.ON_IMPORT) ?: return
        val csv = chooseCsv(ChooseFileTo.OPEN) ?: return

        val seoKeywords = mutableListOf<SeoKeyword>()
        CsvHandler(csv)
            .linesAsSequence()
            .forEachIndexed { i, lines ->
                if (i == 0) {
                    graphClusters.getKeywordContext().clear()
                    graphClusters.getKeywordContext().addAll(lines)
                } else {
                    val keyword = lines[0]
                    val otherMetas = lines.subList(1, lines.lastIndex)
                    seoKeywords.add(SeoKeyword(keyword, otherMetas))
                } }
        graphClusters.clear()
        graphClusters["root"] = GraphClusterItem("root", seoKeywords)
    }

    override fun exportProject() {
        val csv = chooseCsv(ChooseFileTo.SAVE) ?: return

        val root: GraphClusterItem = graphClusters["root"]!!
        val csvHeader = graphClusters.getKeywordContext().joinToString(separator = ",")
        csv.bufferedWriter().use { writer ->
            writer.write("Group name,$csvHeader\n")

            fun writeItem(key: String, item: GraphClusterItem, visited: MutableSet<String>) {
                if (key in visited) return
                visited.add(key)

                val seoKeywords = item.seoKeywords()
                val neighbors = item.neighbors()

                if (seoKeywords.isEmpty()) {
                    writer.write("$key,,\n") //FIXME
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

    private fun chooseCsv(to: ChooseFileTo): File? {
        val csv = FileChooser().apply {
            extensionFilters.addAll(
                ExtensionFilter("Comma-Separated Values", "*.csv"))
        }
        return if (to == ChooseFileTo.SAVE)
            csv.showSaveDialog(this.scene.window)
        else csv.showOpenDialog(this.scene.window)
    }

    private fun chooseSeoclztr(to: ChooseFileTo): File? {
        val seoclztr = FileChooser().apply {
            extensionFilters.addAll(
                ExtensionFilter("Clusterizator", "*.seoclztr"))
        }
        return if (to == ChooseFileTo.SAVE)
            seoclztr.showSaveDialog(this.scene.window)
        else seoclztr.showOpenDialog(this.scene.window)
    }

    private fun areThereNoClustersOrElseDialog(type: Type): Any? {
        if (graphClusters.getMap().isEmpty().not()) {
            val result = NewProjectConfirmationDialog(type).showAndWait()
            if (result.get() == ButtonType.CANCEL) return null
        }
        return Any()
    }

    enum class ChooseFileTo {
        SAVE, OPEN
    }
}