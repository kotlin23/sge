module com.kc.lesson2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.kc.lesson2 to javafx.fxml;
    exports com.kc.lesson2;
}