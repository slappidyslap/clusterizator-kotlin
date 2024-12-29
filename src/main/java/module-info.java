module kg.musabaev.cluserizator {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.scenicview.scenicview;
    requires de.saxsys.mvvmfx;
//    requires de.saxsys.mvvmfx.easydi;

    opens kg.musabaev.cluserizator to javafx.fxml, de.saxsys.mvvmfx, de.saxsys.mvvmfx.easydi;
    exports kg.musabaev.cluserizator;
}