/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Alerts
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class View {

    /**
     * Displays alert
     *
     * @param message message to display
     */
    public void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

}
