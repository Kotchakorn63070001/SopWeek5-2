package com.example.week5sop;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SentenceConsumer {
    protected Sentence sentences = new Sentence();

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        sentences.badSentences.add(s);
        String text = "";
        for (int i=0; i<sentences.badSentences.size(); i++){
            if (i>=1){
                text = text+", ";
            }
            text = text+""+sentences.badSentences.get(i);

        }
        System.out.println("In addBadSentence Method : ["+text+"]");
        System.out.println(sentences.badSentences);
//        return "["+text+"]";
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        sentences.goodSentences.add(s);
        String text = "";
        for (int i=0; i<sentences.goodSentences.size(); i++){
            if (i>=1){
                text = text+", ";
            }
            text = text+""+sentences.goodSentences.get(i);
        }
        System.out.println("In addGoodSentence Method : ["+text+"]");
        System.out.println(getSentences().goodSentences);
//        return "["+text+"]";
    }

    @RabbitListener(queues = "GetQueue")
    public Sentence getSentences(){
        return sentences;
    }
}
