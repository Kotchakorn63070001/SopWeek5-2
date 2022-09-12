package com.example.week5sop;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Word words = new Word();
    protected SentenceConsumer sentenceConsumer = new SentenceConsumer();

    @RequestMapping(value = "/addBad", method = RequestMethod.POST)
    public ArrayList<String> addBadWord(@RequestBody MultiValueMap<String, String> badWord){
        Map<String, String> d = badWord.toSingleValueMap();
        words.badWords.add(d.get("badWord"));
        return words.badWords;
    }

    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s){
        words.badWords.remove(s);
        return  words.badWords;
    }

    @RequestMapping(value = "/addGood", method = RequestMethod.POST)
    public ArrayList<String> addGoodWord(@RequestBody MultiValueMap<String, String> goodWord){
        Map<String, String> d = goodWord.toSingleValueMap();
        words.goodWords.add(d.get("goodWord"));
        return words.goodWords;
    }

    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s){
        words.goodWords.remove(s);
        return words.goodWords;
    }

    @RequestMapping(value = "/proof", method = RequestMethod.POST)
    public String proofSentence(@RequestBody MultiValueMap<String, String> sentence) {
        boolean isFoundGood = false;
        boolean isFoundBad = false;
        Map<String, String> s = sentence.toSingleValueMap();

        for (int i = 0; i < words.goodWords.size(); i++) {
            String checkGood = words.goodWords.get(i);
            isFoundGood = s.get("sentence").contains(checkGood);
            if (isFoundGood) {
                break;
            }
        }

        for (int j = 0; j < words.badWords.size(); j++) {
            String checkBad = words.badWords.get(j);
            isFoundBad = s.get("sentence").contains(checkBad);
            if (isFoundBad) {
                break;
            }
        }

        if (isFoundGood && isFoundBad) {
            rabbitTemplate.convertAndSend("Fanout", "", s.get("sentence"));
            return "Found Bad & Good Word";
        } else if (isFoundGood){
            rabbitTemplate.convertAndSend("Direct", "good", s.get("sentence"));
            return "Found Good Word";
        } else if (isFoundBad) {
            rabbitTemplate.convertAndSend("Direct", "bad", s.get("sentence"));
            return "Found Bad Word";
        }
        return "Not Found";
    }

//    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
//    public Sentence getSentence(){
////        rabbitTemplate.convertAndSend("Direct", "queue",   );
//
//        return sentenceConsumer.getSentences();
//    }

}
