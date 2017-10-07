package com.example.vaadincrud.ui;

import com.example.vaadincrud.beans.Book;
import com.example.vaadincrud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class VaadinUI extends UI {

    private final BookRepository repo;
    private final BookEditor editor;
    final Grid<Book> grid;
    final TextField filter;

    private final Button addNewBtn;

    @Autowired
    public VaadinUI(BookRepository repo, BookEditor editor) {
        this.repo = repo;
        this.editor = editor;

        this.grid = new Grid<>(Book.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New book", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest request) {

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
        setContent(mainLayout);

        grid.setHeight(300, Unit.PIXELS);
        grid.setWidth(1200,Unit.PIXELS);
        grid.setColumns("id", "title", "description","author","isbn","printYear","readAlready");



        filter.setPlaceholder("Filter by title");




        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));


        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editCustomer(e.getValue());

        });


        addNewBtn.addClickListener(e -> editor.createBook(new Book("", "","","","",false)));


        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });


        listCustomers(null);
    }


    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        }
        else {
            grid.setItems(repo.findByTitleStartsWithIgnoreCase(filterText));
        }
    }


}
