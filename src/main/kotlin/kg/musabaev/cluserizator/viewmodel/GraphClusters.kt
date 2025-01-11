package kg.musabaev.cluserizator.viewmodel

import javafx.collections.FXCollections.observableHashMap
import javafx.collections.ObservableMap
import javax.inject.Singleton

@Singleton
class GraphClusters {
    val map: ObservableMap<String, GraphClusterValue> = observableHashMap()

    operator fun get(id: String): GraphClusterValue? = map[id]
    operator fun set(id: String, cluster: GraphClusterValue) { map[id] = cluster }
}
