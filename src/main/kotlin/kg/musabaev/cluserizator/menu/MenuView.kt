package kg.musabaev.cluserizator.menu

import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import kg.musabaev.cluserizator.viewmodel.SeoClusterMapModel
import javax.inject.Inject

class MenuView {
    private val menuBar: MenuBar;
    private lateinit var clusterMapModel: SeoClusterMapModel

    @Inject
    constructor(clusterMapModel: SeoClusterMapModel) {
        this.clusterMapModel = clusterMapModel

        menuBar = MenuBar().apply {
            menus.addAll(
                Menu("Файл").apply {
                    items.addAll(
                        MenuItem("Открыть").apply {
                            setOnAction {
                                // TODO
                            }
                        },
                        MenuItem("Загрузить").apply {
                            // TODO
                            setOnAction {
                                clusterMapModel.clusterMap
                            }
                        }
                    )
                }
            )
        }
    }
}