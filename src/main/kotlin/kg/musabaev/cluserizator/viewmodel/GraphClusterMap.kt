package kg.musabaev.cluserizator.viewmodel

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.ObservableMap
import javax.inject.Singleton

@Singleton
class GraphClusterMap /*: ObservableMapWrapper<String, ObservableList<SeoKeywordModel>>(HashMap<String, ObservableList<SeoKeywordModel>>())*/ {
    val map = FXCollections.observableHashMap<String, GraphClusterValue>()
}

/*fun ObservableMap<GraphNode, ObservableList<GraphNode>>.getById(id: String): ObservableList<GraphNode>? {
    return this[GraphNode(id, emptyList())]
}*/
