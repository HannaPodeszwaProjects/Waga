package pl.polsl.view;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public interface NotificationsInterface {

    default void showNotification(String title, String text, Integer duration){
        Notifications notifications = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(duration))
                .position(Pos.CENTER);
        notifications.show();
    }
}
