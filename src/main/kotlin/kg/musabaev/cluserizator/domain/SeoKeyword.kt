package kg.musabaev.cluserizator.domain

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableArrayList

class SeoKeyword(keyword: String, otherMetas: List<String>) {

    private val keywordProperty = SimpleStringProperty(keyword)
    private val otherMetasProperty = observableArrayList(otherMetas)

    fun getKeyword() = keywordProperty.get()
    fun setKeyword(keyword: String) = keywordProperty.set(keyword)
    fun keywordProperty() = keywordProperty

    fun getOtherMetas() = otherMetasProperty
    fun otherMetas() = otherMetasProperty

    override fun toString(): String {
        return "SeoKeywordModel(keyword=${keywordProperty.get()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SeoKeyword
        return keywordProperty.get() == other.keywordProperty.get()
    }

    override fun hashCode(): Int {
        return keywordProperty.get().hashCode()
    }
}
