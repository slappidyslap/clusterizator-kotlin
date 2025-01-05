package kg.musabaev.cluserizator.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections

class GraphClusterValue(
    clusterId: String,
    seoKeywords: List<SeoKeywordModel>,
    neighborClusterIds: List<String>
) {
    private val clusterIdProperty = SimpleStringProperty(clusterId)
    private val seoKeywordsProperty = FXCollections.observableArrayList(seoKeywords)
    private val neighborClusterIdsProperty = FXCollections.observableArrayList(neighborClusterIds)

    fun getClusterId() = clusterIdProperty.get()
    fun setClusterId(id: String) = clusterIdProperty.set(id)
    fun clusterIdProperty() = clusterIdProperty

    fun neighborClusterIds() = neighborClusterIdsProperty
    fun seoKeywords() = seoKeywordsProperty
}