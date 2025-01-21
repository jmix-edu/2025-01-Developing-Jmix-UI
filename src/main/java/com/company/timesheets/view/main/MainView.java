package com.company.timesheets.view.main;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

@Route("")
@ViewController("ts_MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {

    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit(final InitEvent event) {
        notifications.create("Test caption", "Test description ")
                .withType(Notifications.Type.DEFAULT)
                .withPosition(Notification.Position.BOTTOM_END)
                .show();

    }

    @Install(to = "userIndicator", subject = "formatter")
    private Object userIndicatorFormatter(final UserDetails value) {
        return null;
    }
    
}
