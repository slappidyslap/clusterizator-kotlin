package kg.musabaev.cluserizator.menu

import de.saxsys.mvvmfx.JavaView
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javax.inject.Singleton

@Singleton
abstract class MenuView : MenuBar(), JavaView<MenuViewModel> {
    init {
        menus.addAll(Menu("Файл").apply {
            items.addAll(
                MenuItem("Сохранить").apply { setOnAction { saveProject() } },
                MenuItem("Загрузить").apply { setOnAction { loadProject() } },
                MenuItem("Импортировать").apply { setOnAction { importCsv() } },
                MenuItem("Экспортировать").apply { setOnAction { exportProject() } }
            )
        })
    }

    abstract fun loadProject()

    abstract fun saveProject()

    abstract fun importCsv()

    abstract fun exportProject()
}
