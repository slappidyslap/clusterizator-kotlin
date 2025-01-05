package kg.musabaev.cluserizator.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableArrayList

private class ClusterNode(id: String, seoKeywords: List<SeoKeywordModel>, adjacentNodes: List<ClusterNode>) {
    private val idProperty = SimpleStringProperty(id)
    private val adjacentNodesProperty = observableArrayList(adjacentNodes)
    private val seoKeywords = observableArrayList(seoKeywords)
    
    fun getId() = idProperty.get()
    fun setId(id: String) = idProperty.set(id)
    fun idProperty() = idProperty

    fun getAdjacentNodes() = adjacentNodesProperty

    fun getSeoKeywords() = seoKeywords
}
