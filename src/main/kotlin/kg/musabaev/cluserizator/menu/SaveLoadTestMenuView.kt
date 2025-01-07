package kg.musabaev.cluserizator.menu

import javafx.collections.FXCollections.observableArrayList
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.viewmodel.GraphClusterMap
import kg.musabaev.cluserizator.viewmodel.GraphClusterValue
import kg.musabaev.cluserizator.viewmodel.SeoKeywordModel
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveLoadTestMenuView() : MenuView() {

    private lateinit var graphClusterMap: GraphClusterMap

    @Inject
    constructor(graphClusterMap: GraphClusterMap) : this() {
        this.graphClusterMap = graphClusterMap
    }

    override fun loadFile() {
        ObjectInputStream(FileInputStream("test.seoclztr")).use { input ->
            val graphClusterMapFromFile = input.readObject() as GraphClusterMap
            graphClusterMap.map.putAll(graphClusterMapFromFile.map)
//            graphClusterMapFromFile = null ??? TODO() в памяти все еще хранится объект graphClusterMapFromFile и graphClusterMap.map
        }
    }

    override fun saveFile() {
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

        ObjectOutputStream(FileOutputStream("test.seoclztr")).use { output ->
            output.writeObject(graphClusterMap)
        }
    }
}