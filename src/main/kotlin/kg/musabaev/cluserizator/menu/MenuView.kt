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
                MenuItem("Сохранить").apply { setOnAction { saveFile() } },
                MenuItem("Загрузить").apply { setOnAction { loadFile() } }
            )
        })
    }

    abstract fun loadFile()

    abstract fun saveFile()
}
