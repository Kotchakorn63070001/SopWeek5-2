package com.example.week5sop;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "/index2")
public class MyView2 extends HorizontalLayout {
    private TextField addWord, addSentence;
    private Button btnAddGood, btnAddBad, btnAddSentence;
    private VerticalLayout panel1, panel2;

    public MyView2() {
        panel1 = new VerticalLayout();
        panel2 = new VerticalLayout();
        addWord = new TextField();
        addWord.setLabel("Add Word");
        addWord.setWidth("100%");
        btnAddGood = new Button("Add Good Word");
        btnAddGood.setWidth("100%");
        btnAddBad = new Button("Add Bad Word");
        btnAddBad.setWidth("100%");
        addSentence = new TextField();
        addSentence.setLabel("Add Sentence");
        addSentence.setWidth("100%");
        btnAddSentence = new Button("Add Sentence");
        btnAddSentence.setWidth("100%");
        panel1.add(addWord, btnAddGood, btnAddBad);
        panel2.add(addSentence, btnAddSentence);
        add(panel1, panel2);
    }
}
