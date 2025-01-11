package kg.musabaev.cluserizator.viewmodel

import com.alibaba.fastjson2.annotation.JSONField
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableArrayList

class GraphClusterValue() {

    constructor(
        parentClusterId: String,
        clusterId: String, // TODO убрать тавтологию
        seoKeywords: List<SeoKeywordModel>,
        neighborClusters: List<GraphClusterValue>
    ) : this() {
        parentClusterIdProperty.set(parentClusterId)
        clusterIdProperty.set(clusterId)
        seoKeywordsProperty.addAll(seoKeywords)
        neighborClustersProperty.addAll(neighborClusters)
    }

    constructor(parentClusterId: String, clusterId: String, seoKeywords: List<SeoKeywordModel>)
            : this(parentClusterId, clusterId, seoKeywords, mutableListOf())

    constructor(clusterId: String, seoKeywords: List<SeoKeywordModel>, neighborCluster: List<GraphClusterValue>)
            : this("", clusterId, seoKeywords, neighborCluster)

    constructor(clusterId: String, seoKeywords: List<SeoKeywordModel>)
            : this("", clusterId, seoKeywords, mutableListOf())

    private val parentClusterIdProperty = SimpleStringProperty()
    private val clusterIdProperty = SimpleStringProperty()
    private val seoKeywordsProperty = observableArrayList<SeoKeywordModel>()
    private val neighborClustersProperty = observableArrayList<GraphClusterValue>()

    @JSONField(ordinal = 1)
    fun getParentClusterId() = parentClusterIdProperty.get() ?: ""
    fun setParentClusterId(id: String) = parentClusterIdProperty.set(id)
    fun parentClusterIdProperty() = parentClusterIdProperty

    @JSONField(ordinal = 2)
    fun getClusterId() = clusterIdProperty.get()
    fun setClusterId(id: String) = clusterIdProperty.set(id)
    fun clusterIdProperty() = clusterIdProperty

    @JSONField(ordinal = 3)
    fun getSeoKeywordsAsList() = seoKeywordsProperty.toList()
    fun seoKeywords() = seoKeywordsProperty

    @JSONField(ordinal = 4)
    fun getNeighborClustersAsList() = neighborClustersProperty.toList()
    fun neighborClusters() = neighborClustersProperty

    override fun toString(): String {
        return "GraphClusterValue(" +
                "\n\tparentClusterIdProperty=${parentClusterIdProperty.get()}," +
                "\n\tclusterIdProperty=${clusterIdProperty.get()}," +
                "\n\tseoKeywordsCount=${seoKeywordsProperty.size}," +
                "\n\tneighborClustersProperty=$neighborClustersProperty)"
    }
}
