package kg.musabaev.cluserizator.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableArrayList

class SeoKeyword() {

    constructor(keyword: String, otherMetas: List<String>) : this() {
        keywordProperty.set(keyword)
        otherMetasProperty.addAll(otherMetas)
    }
    private val keywordProperty = SimpleStringProperty()
    private val otherMetasProperty = observableArrayList<String>()

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