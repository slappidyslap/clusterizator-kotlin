package kg.musabaev.cluserizator.menu

import de.saxsys.mvvmfx.ViewModel
import javafx.beans.property.SimpleBooleanProperty
import javax.inject.Singleton

@Singleton
class MenuViewModel : ViewModel {
    private val isDarkModeEnabled = SimpleBooleanProperty(true) // TODO
    private val isLoadingFromSave = SimpleBooleanProperty(false)

    fun getIsDarkModeEnabled() = isDarkModeEnabled.get()
    fun getIsLoadingFromSave() = isLoadingFromSave.get()

    fun setIsDarkModeEnabled(b: Boolean) = isDarkModeEnabled.set(b)
    fun setIsLoadingFromSave(b: Boolean) = isLoadingFromSave.set(b)

    fun isDarkModeEnabledProperty() = isDarkModeEnabled
    fun isLoadingFromSaveProperty() = isLoadingFromSave
}
