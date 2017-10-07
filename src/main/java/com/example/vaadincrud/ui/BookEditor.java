package com.example.vaadincrud.ui;

import com.example.vaadincrud.beans.Book;
import com.example.vaadincrud.repository.BookRepository;
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
public class BookEditor extends VerticalLayout {

    private final BookRepository repository;


    private Book customer;


    TextField title = new TextField("Title");
    TextField description = new TextField("Description");
    TextField author = new TextField("Author");
    TextField isbn = new TextField("ISBN");
    TextField printYear = new TextField("Print Year");





    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);
    CssLayout actions = new CssLayout(save, cancel, delete);

    Binder<Book> binder = new Binder<>(Book.class);

    @Autowired
    public BookEditor(BookRepository repository) {
        this.repository = repository;


        addComponents(title, description, author, isbn, printYear, actions);


        binder.bindInstanceFields(this);


        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);


        save.addClickListener(e -> repository.save(customer));
        delete.addClickListener(e -> repository.delete(customer));
        cancel.addClickListener(e -> createBook(customer));
        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }


    public final void createBook(Book c) {
        if (c == null) {
            setVisible(false);
            return;
        }


        final boolean persisted = c.getId() != null;
        if (persisted) {

            customer = repository.findOne(c.getId());
        }
        else {
            customer = c;
        }

        binder.setBean(customer);

        setVisible(true);


        save.focus();

        title.selectAll();
    }
    public final void editCustomer(Book c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        author.setEnabled(false);


        final boolean persisted = c.getId() != null;
        if (persisted) {

            customer = repository.findOne(c.getId());
        }
        else {
            customer = c;
        }
        if(!c.isReadAlready()){
            c.setReadAlready(true);
        }
        cancel.setVisible(persisted);


        binder.setBean(customer);

        setVisible(true);


        save.focus();

        title.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {

        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}
