package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.FXCollections.observableArrayList
import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput
import javax.inject.Singleton

class SeoKeywordModel() : ViewModel, Externalizable {

    constructor(keyword: String, otherMetas: Array<String>) : this() {
        keywordProperty.set(keyword)
        otherMetasProperty.addAll(otherMetas)
    }
    private val keywordProperty = SimpleStringProperty()
    private val otherMetasProperty = observableArrayList<String>()

    fun getKeyword() = keywordProperty.get()

    fun setKeyword(keyword: String) = keywordProperty.set(keyword)

    fun keywordProperty() = keywordProperty
    fun otherMetas() = otherMetasProperty

    override fun toString(): String {
        return "SeoKeywordModel(keyword=${keywordProperty.get()})"
    }

    override fun writeExternal(out: ObjectOutput) {
        out.writeUTF(getKeyword())
        out.writeObject(otherMetasProperty.toList())
    }

    override fun readExternal(input: ObjectInput) {
        setKeyword(input.readUTF())
        otherMetas().addAll(observableArrayList(input.readObject() as List<String>))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SeoKeywordModel
        return keywordProperty.get() == other.keywordProperty.get()
    }

    override fun hashCode(): Int {
        return keywordProperty.get().hashCode()
    }
}