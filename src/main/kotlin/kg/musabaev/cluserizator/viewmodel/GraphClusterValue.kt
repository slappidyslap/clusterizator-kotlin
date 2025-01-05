package kg.musabaev.cluserizator.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableList
import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput

class GraphClusterValue(): Externalizable {

    constructor(clusterId: String, seoKeywords: List<SeoKeywordModel>, neighborClusterIds: List<String>) : this() {
        clusterIdProperty.set(clusterId)
        seoKeywordsProperty.addAll(seoKeywords)
        neighborClusterIdsProperty.addAll(neighborClusterIds)
    }

    private val clusterIdProperty = SimpleStringProperty()
    private val seoKeywordsProperty = observableArrayList<SeoKeywordModel>()
    private val neighborClusterIdsProperty = observableArrayList<String>()

    fun getClusterId() = clusterIdProperty.get()
    fun setClusterId(id: String) = clusterIdProperty.set(id)
    fun clusterIdProperty() = clusterIdProperty

    fun neighborClusterIds() = neighborClusterIdsProperty
    fun seoKeywords() = seoKeywordsProperty

    override fun writeExternal(out: ObjectOutput) {
        out.writeUTF(clusterIdProperty.get())
        out.writeObject(seoKeywords().toList())
        out.writeObject(neighborClusterIds().toList())
    }

    override fun readExternal(input: ObjectInput) {
        setClusterId(input.readUTF())
        seoKeywords().addAll(observableArrayList(input.readObject() as List<SeoKeywordModel>))
        neighborClusterIds().addAll(observableArrayList(input.readObject() as List<String>))
    }
}