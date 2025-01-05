package kg.musabaev.cluserizator.menu

import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableMap
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.viewmodel.GraphClusterMap
import kg.musabaev.cluserizator.viewmodel.GraphClusterValue
import kg.musabaev.cluserizator.viewmodel.SeoKeywordModel
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.concurrent.atomic.AtomicReference
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
        val i = AtomicReference(1)
        val a = observableArrayList<SeoKeywordModel>()
        TestCsvFileHandler().getLinesCsv().forEach { line ->
            println("initTableView$i")
            val values = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val otherMetas = values.copyOfRange(1, values.lastIndex - 1)
            val id = i.get()
            val keyword = values[0]

            val seoKeyword = SeoKeywordModel(id, keyword, otherMetas)
            i.getAndSet(i.get() + 1)
            a.add(seoKeyword)
        }
        val node22 = GraphClusterValue(
            clusterId = "22",
            seoKeywords = observableArrayList(a.subList(a.size / 2, a.size)),
            neighborClusterIds = emptyList())
        graphClusterMap.map["11"] = GraphClusterValue(
            clusterId = "11",
            seoKeywords = observableArrayList(a.subList(0, a.size / 2)),
            neighborClusterIds = listOf("22"))
        graphClusterMap.map["22"] = node22

        ObjectOutputStream(FileOutputStream("test.seoclztr")).use { output ->
            output.writeObject(graphClusterMap)
        }
    }
}