package com.vaadin.spring.ims.ui.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import com.vaadin.spring.ims.backend.entity.Intern;

import java.util.List;

public class InternForm extends FormLayout {

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    TextField collegeName = new TextField("College Name");
    TextField workInProgressDetails = new TextField("Work in progress details");

    Button save = new Button("Save");
    Button update = new Button("Update");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Intern> binder = new BeanValidationBinder<>(Intern.class);
    private Intern intern;

    public InternForm(List<Intern> interns) {
        addClassName("contact-form");

        binder.bindInstanceFields(this);

        add(
            firstName,
            lastName,
            email,
                collegeName,
            workInProgressDetails,
            createButtonsLayout()
        );
    }

    public void setIntern(Intern intern) {
        this.intern = intern;
        binder.readBean(intern);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        update.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, intern)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));


        return new HorizontalLayout(save,update, delete, close);
    }

    private void validateAndSave() {

      try {
        binder.writeBean(intern);
        fireEvent(new SaveEvent(this, intern));
      } catch (ValidationException e) {
        e.printStackTrace();
      }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<InternForm> {
      private Intern intern;

      protected ContactFormEvent(InternForm source, Intern intern) {
        super(source, false);
        this.intern = intern;
      }

      public Intern getIntern() {
        return intern;
      }
    }

    public static class SaveEvent extends ContactFormEvent {
      SaveEvent(InternForm source, Intern intern) {
        super(source, intern);
      }
    }

    public static class DeleteEvent extends ContactFormEvent {
      DeleteEvent(InternForm source, Intern intern) {
        super(source, intern);
      }

    }

    public static class CloseEvent extends ContactFormEvent {
      CloseEvent(InternForm source) {
        super(source, null);
      }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
      return getEventBus().addListener(eventType, listener);
    }
}
