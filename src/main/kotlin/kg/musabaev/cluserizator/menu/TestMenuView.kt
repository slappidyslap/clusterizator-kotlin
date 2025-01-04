package kg.musabaev.cluserizator.menu

import javafx.collections.FXCollections
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.viewmodel.SeoClusterMapModel
import kg.musabaev.cluserizator.viewmodel.SeoKeywordModel
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestMenuView() : MenuView() {

    private lateinit var clusterMapModel: SeoClusterMapModel

    @Inject
    constructor(clusterMapModel: SeoClusterMapModel) : this() {
        this.clusterMapModel = clusterMapModel
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
        clusterMapModel.clusterMap["1"] = FXCollections.observableArrayList(a.subList(0, a.size / 2))
        clusterMapModel.clusterMap["2"] = FXCollections.observableArrayList(a.subList(a.size / 2, a.size))
        println("loadFile $clusterMapModel")
    }

    override fun saveFile() {
        TODO("Not yet implemented")
    }
}