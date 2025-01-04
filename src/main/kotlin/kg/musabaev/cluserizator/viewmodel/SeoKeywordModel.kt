package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javax.inject.Singleton

@Singleton
class SeoKeywordModel(id: String, keyword: String, otherMetas: Array<String>) : ViewModel {

    private val idProperty = SimpleStringProperty(id)
    private val keywordProperty = SimpleStringProperty(keyword)
    private val otherMetasProperty = FXCollections.observableArrayList(otherMetas)

    fun getId(): String { return idProperty.get() }
    fun getKeyword(): String { return keywordProperty.get() }

    fun setId(id: String) { idProperty.set(id) }
    fun setKeyword(keyword: String) { keywordProperty.set(keyword) }

    fun idProperty(): SimpleStringProperty { return idProperty }
    fun keywordProperty(): SimpleStringProperty { return keywordProperty }

    override fun toString(): String {
        return "SeoKeywordModel(keyword=${keywordProperty.get()})"
    }
}