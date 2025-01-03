package kg.musabaev.cluserizator

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javax.inject.Singleton

@Singleton
class SeoKeywordModel(id: Int, keyword: String, val otherMetas: Array<String>) : ViewModel {
    private val id = SimpleIntegerProperty(id)
    private val keyword = SimpleStringProperty(keyword)

    fun getId(): Int {
        return id.get()
    }

    fun setId(id: Int) {
        this.id.set(id)
    }

    fun idProperty(): SimpleIntegerProperty {
        return id
    }

    fun getKeyword(): String {
        return keyword.get()
    }

    fun setKeyword(keyword: String?) {
        this.keyword.set(keyword)
    }

    fun keywordProperty(): SimpleStringProperty {
        return keyword
    }
}