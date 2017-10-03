package com.example.vaadincrud.ui;

import com.example.vaadincrud.beans.Book;
import com.example.vaadincrud.repository.CustomerRepository;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout {

    private final CustomerRepository repository;

    /**
     * The currently edited customer
     */
    private Book customer;

    /* Fields to edit properties in Book entity */
    TextField title = new TextField("Title");
    TextField description = new TextField("Description");
    TextField author = new TextField("Author");
    TextField isbn = new TextField("ISBN");
    DateField printYear = new DateField("Print Year");
    CheckBox readAlready = new CheckBox("Already read");


    /* Action buttons */
    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);
    CssLayout actions = new CssLayout(save, cancel, delete);

    Binder<Book> binder = new Binder<>(Book.class);

    @Autowired
    public CustomerEditor(CustomerRepository repository) {
        this.repository = repository;

        printYear.setDateFormat("yyyy-MM-dd");


        printYear.setPlaceholder("yyyy-mm-dd");

        addComponents(title, description, author, isbn, printYear,readAlready, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(customer));
        delete.addClickListener(e -> repository.delete(customer));
        cancel.addClickListener(e -> editCustomer(customer));
        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editCustomer(Book c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            customer = repository.findOne(c.getId());
        }
        else {
            customer = c;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(customer);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        title.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}
