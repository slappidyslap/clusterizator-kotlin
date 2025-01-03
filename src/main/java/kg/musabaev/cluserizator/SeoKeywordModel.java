package kg.musabaev.cluserizator;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.inject.Singleton;

@Singleton
public class SeoKeywordModel implements ViewModel {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty keyword;
    private final String[] otherMetas;

    public SeoKeywordModel(int id, String keyword, String[] otherMetas) {
        this.id = new SimpleIntegerProperty(id);
        this.keyword = new SimpleStringProperty(keyword);
        this.otherMetas = otherMetas;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getKeyword() {
        return keyword.get();
    }

    public void setKeyword(String keyword) {
        this.keyword.set(keyword);
    }

    public SimpleStringProperty keywordProperty() {
        return keyword;
    }

    public String[] getOtherMetas() {
        return otherMetas;
    }
}
