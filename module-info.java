module Project {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;

    exports model;
    exports service;
    exports exception;
    exports gui;
}