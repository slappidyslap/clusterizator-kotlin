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

    constructor(id: Int, keyword: String, otherMetas: Array<String>) : this() {
        idProperty.set(id)
        keywordProperty.set(keyword)
        otherMetasProperty.addAll(otherMetas)
    }

    private val idProperty = SimpleIntegerProperty()
    private val keywordProperty = SimpleStringProperty()
    private val otherMetasProperty = observableArrayList<String>()

    fun getId() = idProperty.get()
    fun getKeyword() = keywordProperty.get()

    fun setId(id: Int) = idProperty.set(id)
    fun setKeyword(keyword: String) = keywordProperty.set(keyword)

    fun idProperty() = idProperty
    fun keywordProperty() = keywordProperty
    fun otherMetas() = otherMetasProperty

    override fun toString(): String {
        return "SeoKeywordModel(keyword=${keywordProperty.get()})"
    }

    override fun writeExternal(out: ObjectOutput) {
        out.writeInt(getId())
        out.writeUTF(getKeyword())
        out.writeObject(otherMetasProperty.toList())
    }

    override fun readExternal(input: ObjectInput) {
        setId(input.readInt())
        setKeyword(input.readUTF())
        otherMetas().addAll(observableArrayList(input.readObject() as List<String>))
    }
}