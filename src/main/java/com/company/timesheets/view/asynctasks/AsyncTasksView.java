package com.company.timesheets.view.asynctasks;


import com.company.timesheets.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.asynctask.UiAsyncTasks;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

@Route(value = "async-tasks-view", layout = MainView.class)
@ViewController(id = "ts_AsyncTasksView")
@ViewDescriptor(path = "async-tasks-view.xml")
public class AsyncTasksView extends StandardView {

    @Autowired
    private UiAsyncTasks uiAsyncTasks;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private TypedTextField<Object> inputField;

    @Subscribe(id = "asyncWithoutResultBtn", subject = "clickListener")
    public void onAsyncWithoutResultBtnClick(final ClickEvent<JmixButton> event) {
        uiAsyncTasks.runnableConfigurer(this::myMethod)
                .withResultHandler(() -> {
                    notifications.show("Void method completed");
                })
                .runAsync();
    }

    @Subscribe(id = "asyncWithResultBtn", subject = "clickListener")
    public void onAsyncWithResultBtnClick(final ClickEvent<JmixButton> event) {
        String typed = inputField.getValue();

        uiAsyncTasks.supplierConfigurer(() -> changeString(typed))
                .withResultHandler(resultString -> {
                    notifications.show(resultString);
                })
                .withTimeout(3, TimeUnit.SECONDS)
                .withExceptionHandler(ex -> {
                    if (ex instanceof TimeoutException) {
                        notifications.create("Timeout exception!")
                                .withType(Notifications.Type.WARNING)
                                .show();
                    } else if (ex instanceof InterruptedException) {
                        notifications.create("Interrupted exception!")
                                .withType(Notifications.Type.WARNING)
                                .show();
                    } else {
                        notifications.create("Unknown error")
                                .withType(Notifications.Type.WARNING)
                                .show();
                    }

                })
                .supplyAsync();
    }

    private void myMethod() {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String changeString(String passed) {

        try {
            sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return (passed + " changed").toUpperCase();
    }
}