package kg.musabaev.cluserizator.viewmodel

import javafx.collections.FXCollections
import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput
import javax.inject.Singleton

@Singleton
class GraphClusterMap : Externalizable /*: ObservableMapWrapper<String, ObservableList<SeoKeywordModel>>(HashMap<String, ObservableList<SeoKeywordModel>>())*/ {
    val map = FXCollections.observableHashMap<String, GraphClusterValue>()

    override fun writeExternal(out: ObjectOutput) {
        out.writeObject(map.values.toList())
    }

    override fun readExternal(input: ObjectInput) {
        map.clear()
        val mapValues = input.readObject() as List<GraphClusterValue>
        for (mapValue in mapValues) {
            map[mapValue.getClusterId()] = mapValue
        }
    }
}

/*fun ObservableMap<GraphNode, ObservableList<GraphNode>>.getById(id: String): ObservableList<GraphNode>? {
    return this[GraphNode(id, emptyList())]
}*/
