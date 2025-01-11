package kg.musabaev.cluserizator.viewmodel

import com.alibaba.fastjson2.annotation.JSONField
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableArrayList

class GraphClusterValue() {

    constructor(
        parentClusterId: String,
        clusterId: String, // TODO убрать тавтологию
        seoKeywords: List<SeoKeyword>,
        neighborClusters: List<GraphClusterValue>
    ) : this() {
        parentClusterIdProperty.set(parentClusterId)
        clusterIdProperty.set(clusterId)
        seoKeywordsProperty.addAll(seoKeywords)
        neighborClustersProperty.addAll(neighborClusters)
    }

    constructor(parentClusterId: String, clusterId: String, seoKeywords: List<SeoKeyword>)
            : this(parentClusterId, clusterId, seoKeywords, mutableListOf())

    constructor(clusterId: String, seoKeywords: List<SeoKeyword>, neighborCluster: List<GraphClusterValue>)
            : this("", clusterId, seoKeywords, neighborCluster)

    constructor(clusterId: String, seoKeywords: List<SeoKeyword>)
            : this("", clusterId, seoKeywords, mutableListOf())

    private val parentClusterIdProperty = SimpleStringProperty()
    private val clusterIdProperty = SimpleStringProperty()
    private val seoKeywordsProperty = observableArrayList<SeoKeyword>()
    private val neighborClustersProperty = observableArrayList<GraphClusterValue>()

    @JSONField(name = "parentClusterId", ordinal = 1)
    fun getParentClusterId() = parentClusterIdProperty.get() ?: ""
    fun setParentClusterId(id: String) = parentClusterIdProperty.set(id)
    fun parentClusterIdProperty() = parentClusterIdProperty

    @JSONField(name = "clusterId", ordinal = 2)
    fun getClusterId() = clusterIdProperty.get()
    fun setClusterId(id: String) = clusterIdProperty.set(id)
    fun clusterIdProperty() = clusterIdProperty

    @JSONField(name = "seoKeywords", ordinal = 3)
    fun getSeoKeywordsAsList() = seoKeywordsProperty.toList()
    fun seoKeywords() = seoKeywordsProperty

    @JSONField(name = "neighborClusters", ordinal = 4)
    fun getNeighborClustersAsList() = neighborClustersProperty.toList()
    fun neighborClusters() = neighborClustersProperty

    override fun toString(): String {
        return "GraphClusterValue(" +
                "\n\tparentClusterIdProperty=${parentClusterIdProperty.get()}," +
                "\n\tclusterIdProperty=${clusterIdProperty.get()}," +
                "\n\tseoKeywordsCount=${seoKeywordsProperty.size}," +
                "\n\tneighborClustersProperty=$neighborClustersProperty)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GraphClusterValue
        return clusterIdProperty == other.clusterIdProperty
    }

    override fun hashCode(): Int {
        return clusterIdProperty.hashCode()
    }
}
