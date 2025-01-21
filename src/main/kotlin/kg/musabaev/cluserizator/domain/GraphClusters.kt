package kg.musabaev.cluserizator.domain

import com.alibaba.fastjson2.annotation.JSONField
import javafx.collections.FXCollections.observableHashMap
import javafx.collections.ObservableMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphClusters {

    private val mapProperty: ObservableMap<String, GraphClusterItem> = observableHashMap()
    private val keywordContextProperty = mutableListOf<String>()

    @JSONField(ordinal = 1, name = "keywordContext")
    fun getKeywordContext() = keywordContextProperty
    @JSONField(ordinal = 2, name = "rootCluster")
    fun getRootCluster() = mapProperty["root"]!!
    @JSONField(serialize = false, deserialize = false)
    fun getMap() = mapProperty

    @Inject
    constructor()

    constructor(keywordContext: List<String>, rootCluster: GraphClusterItem) : this() {
        keywordContextProperty.addAll(keywordContext)
        putClustersRecursively(rootCluster)
        mapProperty["root"] = rootCluster
    }

    operator fun get(id: String): GraphClusterItem? = mapProperty[id]
    operator fun set(id: String, cluster: GraphClusterItem) { mapProperty[id] = cluster }
    fun clear() {
        mapProperty.clear()
    }

    private fun putClustersRecursively(cluster: GraphClusterItem) {
        if (cluster.neighbors().isEmpty()) return
        for (neighbor in cluster.neighbors()) {
            putClustersRecursively(neighbor)
            mapProperty[neighbor.getId()] = neighbor
        }
    }
}
