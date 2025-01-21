package kg.musabaev.cluserizator.domain

import com.alibaba.fastjson2.annotation.JSONField
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableArrayList

class GraphClusterItem() {

    constructor(
        parentId: String,
        id: String,
        seoKeywords: List<SeoKeyword>,
        neighbors: List<GraphClusterItem>
    ) : this() {
        parentIdProperty.set(parentId)
        idProperty.set(id)
        seoKeywordsProperty.addAll(seoKeywords)
        neighborsProperty.addAll(neighbors)
    }

    constructor(parentId: String, id: String, seoKeywords: List<SeoKeyword>)
            : this(parentId, id, seoKeywords, mutableListOf())

    constructor(id: String, seoKeywords: List<SeoKeyword>, neighbors: List<GraphClusterItem>)
            : this("", id, seoKeywords, neighbors)

    constructor(id: String, seoKeywords: List<SeoKeyword>)
            : this("", id, seoKeywords, mutableListOf())

    private val parentIdProperty = SimpleStringProperty()
    private val idProperty = SimpleStringProperty()
    private val seoKeywordsProperty = observableArrayList<SeoKeyword>()
    private val neighborsProperty = observableArrayList<GraphClusterItem>()

    @JSONField(name = "parentId", ordinal = 1)
    fun getParentId() = parentIdProperty.get() ?: ""
    fun setParentId(id: String) = parentIdProperty.set(id)
    fun parentIdProperty() = parentIdProperty

    @JSONField(name = "id", ordinal = 2)
    fun getId() = idProperty.get()
    fun setId(id: String) = idProperty.set(id)
    fun idProperty() = idProperty

    @JSONField(name = "seoKeywords", ordinal = 3)
    fun getSeoKeywordsAsList() = seoKeywordsProperty.toList()
    fun seoKeywords() = seoKeywordsProperty

    @JSONField(name = "neighbors", ordinal = 4)
    fun getNeighborsAsList() = neighborsProperty.toList()
    fun neighbors() = neighborsProperty

    override fun toString(): String {
        return "GraphClusterItem(" +
                "\n\tparentId=${parentIdProperty.get()}," +
                "\n\tid=${idProperty.get()}," +
                "\n\tseoKeywordsCount=${seoKeywordsProperty.size}," +
                "\n\tneighbors=$neighborsProperty)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GraphClusterItem
        return idProperty == other.idProperty
    }

    override fun hashCode(): Int {
        return idProperty.hashCode()
    }
}
