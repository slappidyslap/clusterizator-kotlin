package kg.musabaev.cluserizator.domain

import javafx.collections.FXCollections.observableHashMap
import javafx.collections.ObservableMap
import javax.inject.Singleton

@Singleton
class GraphClusters {
    val map: ObservableMap<String, GraphClusterItem> = observableHashMap()

    operator fun get(id: String): GraphClusterItem? = map[id]
    operator fun set(id: String, cluster: GraphClusterItem) { map[id] = cluster }
}
