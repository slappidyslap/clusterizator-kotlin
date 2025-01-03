package kg.musabaev.cluserizator;

import de.saxsys.mvvmfx.ViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Singleton;

@Singleton
public class MainViewModel implements ViewModel {
    private final ObservableList<SeoKeywordModel> seoKeywords = FXCollections.observableArrayList();

    public ObservableList<SeoKeywordModel> getSeoKeywords() {
        return seoKeywords;
    }
}
