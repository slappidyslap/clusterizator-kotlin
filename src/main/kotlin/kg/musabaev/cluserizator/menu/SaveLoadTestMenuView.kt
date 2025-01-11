package kg.musabaev.cluserizator.menu

import javafx.collections.FXCollections.observableArrayList
import javafx.fxml.Initializable
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.viewmodel.GraphClusterMap
import kg.musabaev.cluserizator.viewmodel.GraphClusterValue
import kg.musabaev.cluserizator.viewmodel.SeoKeywordModel
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
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
                    val a = observableArrayList<SeoKeywordModel>()
                    TestCsvFileHandler().getLinesCsv().forEach { line ->
                        val values = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val otherMetas = values.copyOfRange(1, values.lastIndex - 1)
                        val keyword = values[0]

                        val seoKeyword = SeoKeywordModel(keyword, otherMetas)
                        a.add(seoKeyword)
                    }
                    graphClusterMap.map["root"] = GraphClusterValue(
                        clusterId = "root",
                        seoKeywords = observableArrayList(a))
                }},
                MenuItem("Удалить все кластеры").apply { setOnAction { TODO() } }
            )
        }) // мб потом перенести в initialize
    }

    override fun loadFile() {
        menuViewModel.setIsLoadingFromSave(true)
        ObjectInputStream(FileInputStream("test.seoclztr")).use { input ->
            val graphClusterMapFromFile = input.readObject() as GraphClusterMap
            graphClusterMap.map.putAll(graphClusterMapFromFile.map)
//            graphClusterMapFromFile = null ??? TODO() в памяти все еще хранится объект graphClusterMapFromFile и graphClusterMap.map
        }
        menuViewModel.setIsLoadingFromSave(false)
    }

    override fun saveFile() {
        ObjectOutputStream(FileOutputStream("test.seoclztr")).use { output ->
            output.writeObject(graphClusterMap)
        }
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
//        TODO() срабатывает
    }
}