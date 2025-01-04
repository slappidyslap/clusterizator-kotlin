package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableObjectValue
import javax.inject.Singleton

@Singleton
class GraphViewModel : ViewModel {
    private val selectedGraphIdProperty = SimpleStringProperty()
    private val clusterNode = SimpleObjectProperty<ClusterNode>()

    fun getSelectedGraphId() = selectedGraphIdProperty.get()
    fun setSelectedGraphId(graphId: String) = selectedGraphIdProperty.set(graphId)
    fun selectedGraphIdProperty() = selectedGraphIdProperty

    fun getClusterNode() = clusterNode.get()
    fun setClusterNode(node: ClusterNode) = clusterNode.set(node)
    fun clusterNodeProperty() = clusterNode
}