package com.example.week5sop;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route(value = "/index2")
public class MyView2 extends HorizontalLayout {
    private TextField addWord, addSentence;
    private Button btnAddGood, btnAddBad, btnAddSentence, btnShowSentence;
    private ComboBox goodWord, badWord;
    private TextArea goodSentence, badSentence;
    private VerticalLayout panel1, panel2;
    private Word words = new Word();
//

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
        goodWord = new ComboBox("Good Words");
        goodWord.setWidth("100%");
        goodWord.setItems(words.goodWords);
        badWord = new ComboBox("Bad Words");
        badWord.setWidth("100%");
        badWord.setItems(words.badWords);

        addSentence = new TextField();
        addSentence.setLabel("Add Sentence");
        addSentence.setWidth("100%");
        btnAddSentence = new Button("Add Sentence");
        btnAddSentence.setWidth("100%");
        goodSentence = new TextArea("Good Sentences");
        goodSentence.setWidth("100%");
        badSentence = new TextArea("Bad Sentences");
        badSentence.setWidth("100%");
        btnShowSentence = new Button("Show Sentence");
        btnShowSentence.setWidth("100%");

        panel1.add(addWord, btnAddGood, btnAddBad, goodWord, badWord);
        panel2.add(addSentence, btnAddSentence, goodSentence, badSentence, btnShowSentence);
        add(panel1, panel2);

        btnAddGood.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("goodWord", addWord.getValue());

            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGood")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            Notification insertGood = Notification.show("Insert "+out.get(out.size()-1)+" to Good Word Lists Complete.");
            goodWord.setItems(out);
        });

        btnAddBad.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("badWord", addWord.getValue());

            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addBad")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            Notification insertBad = Notification.show("Insert "+out.get(out.size()-1)+" to Bad Word Lists Complete.");
            badWord.setItems(out);
        });

        btnAddSentence.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("sentence", addSentence.getValue());

            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            Notification msg = Notification.show(out);
        });

    }


}
