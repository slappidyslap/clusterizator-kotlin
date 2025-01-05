package kg.musabaev.cluserizator.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections

class GraphClusterValue(clusterId: String, seoKeywords: List<SeoKeywordModel>, neighbors: List<GraphClusterValue>) {
    private val clusterIdProperty = SimpleStringProperty(clusterId)
    private val seoKeywordsProperty = FXCollections.observableArrayList(seoKeywords)
    private val neighborsProperty = FXCollections.observableArrayList(neighbors)

    fun getClusterId() = clusterIdProperty.get()
    fun setClusterId(id: String) = clusterIdProperty.set(id)
    fun clusterIdProperty() = clusterIdProperty

    fun neighbors() = neighborsProperty
    fun seoKeywords() = seoKeywordsProperty
}