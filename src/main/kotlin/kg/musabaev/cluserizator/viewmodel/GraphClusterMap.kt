package kg.musabaev.cluserizator.viewmodel

import javafx.collections.FXCollections
import javafx.collections.MapChangeListener
import javafx.collections.ObservableMap
import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput
import javax.inject.Singleton

@Singleton
class GraphClusterMap : Externalizable /*: ObservableMapWrapper<String, ObservableList<SeoKeywordModel>>(HashMap<String, ObservableList<SeoKeywordModel>>())*/ {
    val map = FXCollections.observableHashMap<String, GraphClusterValue>()

    private var additionType: AdditionType? = null

    // Метод для добавления значения с определённым типом
    fun putFromSave(key: String, value: GraphClusterValue) {
        this.additionType = AdditionType.FROM_SAVE
        map[key] = value
        this.additionType = null
    }

    fun put(key: String, value: GraphClusterValue) {
        this.additionType = AdditionType.REGULAR
        map[key] = value
        this.additionType = null
    }

//    map.addListener(MapChangeListener { change ->
//        if (change.wasAdded()) {
//            val type = additionType ?: AdditionType.REGULAR
//            handleAddition(change.key, change.valueAdded, type)
//        }
//    })

    private fun handleAddition(key: String, value: GraphClusterValue, type: AdditionType) {
        when (type) {
            AdditionType.REGULAR -> {
                println("Обычное добавление: $key = $value")
            }
            AdditionType.FROM_SAVE -> {
                println("Добавление из сейва: $key = $value")
            }
        }
    }

    enum class AdditionType {
        REGULAR,
        FROM_SAVE,
        // другие типы добавления по необходимости
    }

    override fun writeExternal(out: ObjectOutput) {
        out.writeObject(map.values.toList())
    }

    override fun readExternal(input: ObjectInput) {
        map.clear()
        val mapValues = input.readObject() as List<GraphClusterValue>
        for (mapValue in mapValues) {
            map[mapValue.getClusterId()] = mapValue
        }
    }

    private class ClusterMapChange(
        private val key: String,
        private val old: GraphClusterValue,
        private val added: GraphClusterValue,
        private val wasAdded: Boolean,
        private val wasRemoved: Boolean,
        private val wasAddedFromSave: Boolean,
        observableMap: ObservableMap<String, GraphClusterValue>
    ) : MapChangeListener.Change<String, GraphClusterValue>(observableMap) {

        override fun wasAdded() = this.wasAdded

        override fun wasRemoved() = this.wasRemoved

        fun wasAddedFromSave() = this.wasAddedFromSave

        override fun getKey() = this.key

        override fun getValueAdded() = this.added

        override fun getValueRemoved() = this.old

        override fun toString(): String {
            val var1 = StringBuilder()
            if (this.wasAdded) {
                if (this.wasRemoved)
                    var1.append(this.old).append(" replaced by ").append(this.added)
                else
                    var1.append(this.added).append(" added")
            } else
                var1.append(this.old).append(" removed")

            var1.append(" at key ").append(this.key)
            return var1.toString()
        }
    }
}
