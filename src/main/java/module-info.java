module kg.musabaev.cluserizator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.scenicview.scenicview;
    requires de.saxsys.mvvmfx;
    requires de.saxsys.mvvmfx.easydi;
    requires javax.inject;
    requires kotlin.stdlib;

    opens kg.musabaev.cluserizator to de.saxsys.mvvmfx;
    exports kg.musabaev.cluserizator;
}