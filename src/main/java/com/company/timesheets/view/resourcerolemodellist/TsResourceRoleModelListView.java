package com.company.timesheets.view.resourcerolemodellist;

import com.company.timesheets.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.securityflowui.view.resourcerole.ResourceRoleModelListView;

@Route(value = "sec/resourcerolemodels", layout = MainView.class)
@ViewController(id = "sec_ResourceRoleModel.list")
@ViewDescriptor(path = "ts-resource-role-model-list-view.xml")
public class TsResourceRoleModelListView extends ResourceRoleModelListView {


}