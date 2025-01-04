package kg.musabaev.cluserizator.viewmodel

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javax.inject.Singleton

@Singleton
class SeoClusterMapModel {
    val clusterMap = FXCollections.observableHashMap<String, ObservableList<SeoKeywordModel>>()
}