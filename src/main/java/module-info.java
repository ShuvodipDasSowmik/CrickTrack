module com.example.pms_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires javafx.media;

    opens com.example.pms_project to javafx.fxml;
    opens com.example.pms_project.Classes.PlayerClasses to javafx.base;

    exports com.example.pms_project;
    exports com.example.pms_project.Server;
    opens com.example.pms_project.Server to javafx.fxml;
//    exports com.example.pms_project.Classes;
//    opens com.example.pms_project.Classes to javafx.fxml;
}