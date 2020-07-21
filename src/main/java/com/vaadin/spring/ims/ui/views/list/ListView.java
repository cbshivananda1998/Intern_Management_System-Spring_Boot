package com.vaadin.spring.ims.ui.views.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.spring.ims.backend.entity.Intern;
import com.vaadin.spring.ims.backend.service.InternService;
import com.vaadin.spring.ims.ui.MainLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Intern Details | Intern Management System")
public class ListView extends VerticalLayout {

    InternForm form;
    Grid<Intern> grid = new Grid<>(Intern.class);
    TextField filterByName = new TextField();
    TextField filterByEmail = new TextField();

    InternService internService;

    public ListView(InternService internService) {
        this.internService = internService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        form = new InternForm(internService.findAll());
        form.addListener(InternForm.SaveEvent.class, this::saveContact);
        form.addListener(InternForm.DeleteEvent.class, this::deleteContact);
        form.addListener(InternForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateListByName();
        closeEditor();
    }

    private void deleteContact(InternForm.DeleteEvent evt) {
        internService.delete(evt.getIntern());
        updateListByName();
        closeEditor();
    }

    private void saveContact(InternForm.SaveEvent evt) {
        internService.save(evt.getIntern());
        updateListByName();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filterByName.setPlaceholder("Filter by name...");
        filterByName.setClearButtonVisible(true);
        filterByName.setValueChangeMode(ValueChangeMode.LAZY);
        filterByName.addValueChangeListener(e -> updateListByName());

        filterByEmail.setPlaceholder("Filter by email...");
        filterByEmail.setClearButtonVisible(true);
        filterByEmail.setValueChangeMode(ValueChangeMode.LAZY);
        filterByEmail.addValueChangeListener(e -> updateListByEmail());

        Button addContactButton = new Button("Add Intern", click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterByName, filterByEmail,addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Intern());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email","collegeName","workInProgressDetails");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));
    }

    private void editContact(Intern intern) {
        if (intern == null) {
            closeEditor();
        } else {
            form.setIntern(intern);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setIntern(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateListByName() {
        grid.setItems(internService.findAll(filterByName.getValue()));
    }
    private void updateListByEmail() {
        grid.setItems(internService.findAll(filterByEmail.getValue()));
    }
}
