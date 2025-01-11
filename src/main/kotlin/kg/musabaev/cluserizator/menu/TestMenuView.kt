package kg.musabaev.cluserizator.menu

import javafx.collections.FXCollections.observableArrayList
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.domain.GraphClusters
import kg.musabaev.cluserizator.domain.SeoKeyword
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
private class TestMenuView() : MenuView() {

    private lateinit var graphClusters: GraphClusters

    @Inject
    constructor(graphClusters: GraphClusters) : this() {
        this.graphClusters = graphClusters
    }

    override fun loadFile() {
        val a = observableArrayList<SeoKeyword>()
        TestCsvFileHandler().getLinesCsv().forEach { line ->
            val values = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toList()
            val otherMetas = values.subList(1, values.lastIndex - 1)
            val keyword = values[0]

            val seoKeyword = SeoKeyword(keyword, otherMetas)
            a.add(seoKeyword)
        }
        /*val node22 = GraphClusterValue(
            clusterId = "22",
            seoKeywords = observableArrayList(a.subList(a.size / 2, a.size)))
        graphClusterMap.map["11"] = GraphClusterValue(
            clusterId = "11",
            seoKeywords = observableArrayList(a.subList(0, a.size / 2)),
            neighborClusterIds = listOf("22"))
        graphClusterMap.map["22"] = node22*/
    }

    override fun saveFile() {

    }
}