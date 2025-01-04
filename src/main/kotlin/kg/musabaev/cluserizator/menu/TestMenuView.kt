package kg.musabaev.cluserizator.menu

import javafx.collections.FXCollections
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.viewmodel.ClusterNode
import kg.musabaev.cluserizator.viewmodel.GraphViewModel
import kg.musabaev.cluserizator.viewmodel.SeoKeywordModel
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestMenuView() : MenuView() {

    private lateinit var graphViewModel: GraphViewModel

    @Inject
    constructor(graphViewModel: GraphViewModel) : this() {
        this.graphViewModel = graphViewModel
    }

    override fun loadFile() {
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
        graphViewModel.setClusterNode(ClusterNode(
            id = "1",
            seoKeywords = a.subList(0, a.size / 2),
            adjacentNodes = listOf(ClusterNode(
                id = "2",
                seoKeywords = a.subList(a.size / 2, a.size),
                adjacentNodes = emptyList()
            ))
        ))
    }

    override fun saveFile() {
        TODO("Not yet implemented")
    }
}