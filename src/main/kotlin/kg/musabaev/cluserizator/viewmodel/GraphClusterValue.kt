package kg.musabaev.cluserizator.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableArrayList
import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput

class GraphClusterValue(): Externalizable {

    constructor(
        parentClusterId: String,
        clusterId: String,
        seoKeywords: List<SeoKeywordModel>,
        neighborClusterIds: List<String>
    ) : this() {
        parentClusterIdProperty.set(parentClusterId)
        clusterIdProperty.set(clusterId)
        seoKeywordsProperty.addAll(seoKeywords)
        neighborClusterIdsProperty.addAll(neighborClusterIds)
    }

    constructor(parentClusterId: String, clusterId: String, seoKeywords: List<SeoKeywordModel>)
            : this(parentClusterId, clusterId, seoKeywords, mutableListOf())

    constructor(clusterId: String, seoKeywords: List<SeoKeywordModel>, neighborClusterIds: List<String>)
            : this("", clusterId, seoKeywords, neighborClusterIds)

    constructor(clusterId: String, seoKeywords: List<SeoKeywordModel>)
            : this("", clusterId, seoKeywords, mutableListOf())

    private val parentClusterIdProperty = SimpleStringProperty()
    private val clusterIdProperty = SimpleStringProperty()
    private val seoKeywordsProperty = observableArrayList<SeoKeywordModel>()
    private val neighborClusterIdsProperty = observableArrayList<String>()

    fun getParentClusterId() = parentClusterIdProperty.get()
    fun setParentClusterId(id: String) = parentClusterIdProperty.set(id)
    fun parentClusterIdProperty() = parentClusterIdProperty

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