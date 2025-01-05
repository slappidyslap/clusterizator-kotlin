package kg.musabaev.cluserizator.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections

private class GraphNode(id: String, seoKeywords: List<SeoKeywordModel>) {
    private val idProperty = SimpleStringProperty()
//    private val neighborsProperty = FXCollections.observableArrayList<GraphNode>()
    private val seoKeywordsProperty = FXCollections.observableArrayList<SeoKeywordModel>()

    fun getId() = idProperty.get()
    fun setId(id: String) = idProperty.set(id)
    fun idProperty() = idProperty

//    fun neighbors() = neighborsProperty

    fun seoKeywords() = seoKeywordsProperty

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GraphNode
        return idProperty == other.idProperty
    }

    override fun hashCode(): Int {
        return idProperty.hashCode()
    }
}
