module kg.musabaev.cluserizator {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.scenicview.scenicview;
    requires de.saxsys.mvvmfx;
    requires de.saxsys.mvvmfx.easydi;
    requires javax.inject;

    opens kg.musabaev.cluserizator to javafx.fxml, de.saxsys.mvvmfx;
    exports kg.musabaev.cluserizator;
}