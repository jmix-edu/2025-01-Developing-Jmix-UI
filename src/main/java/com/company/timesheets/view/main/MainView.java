package com.company.timesheets.view.main;

import com.company.timesheets.component.slider.Slider;
import com.company.timesheets.entity.TimeEntry;
import com.company.timesheets.event.TimeEntryStatusChangedEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.Metadata;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.main.JmixListMenu;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;

@Route("")
@ViewController("ts_MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {

    @Autowired
    private Metadata metadata;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private JmixListMenu menu;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit(final InitEvent event) {
        updateRejectedEntriesCounter();
    }

    @EventListener
    private void timeEntryStatusChanged(TimeEntryStatusChangedEvent event) {
        updateRejectedEntriesCounter();
    }

    private void updateRejectedEntriesCounter() {
        LoadContext<TimeEntry> loadContext = new LoadContext<>(metadata.getClass(TimeEntry.class));
        loadContext.setQueryString("select te from ts_TimeEntry te " +
                "where te.user.username = :current_user_username " +
                "and te.status = @enum(com.company.timesheets.entity.TimeEntryStatus.REJECTED)");
        long count = dataManager.getCount(loadContext);

        Span badge = null;
        if (count > 0) {
            badge = new Span("" + count);
            badge.getElement().getThemeList().add("badge error");
        }
        menu.getMenuItem("ts_TimeEntry.my").setSuffixComponent(badge);
    }

    @Subscribe("slider")
    public void onSliderChange(Slider.SlideChangedEvent event) {
        notifications.show("New value is: " + event.getValue());
    }
}
