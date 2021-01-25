package com.sigsauer.exchangerates.controller;

import com.sigsauer.exchangerates.entity.User;
import com.sigsauer.exchangerates.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.Slf4JLoggingSystem;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class Controller extends TelegramLongPollingBot {

    @Autowired
    UserRepository userRepository;

    Map<String, String> userRequests = new HashMap<>();

    String[] currencies = { "USD", "EUR", "RUB"};
    String[] sources = { "PrivatBank", "OschadBank", "Money24", "<-"};

    @Override
    public String getBotUsername() {
        return "CurrencyExchange2021_Bot";
    }

    @Override
    public String getBotToken() {
        return "1575305501:AAHyBGkT9FaUP6ERoWEZDntAj_x1Xtra0tw";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage() != null) {
            Message message = update.getMessage();
            User user = new User(message.getFrom().getFirstName(), message.getFrom().getLastName(),
                    message.getFrom().getUserName(), message.getChatId().toString());
            addUser(user);
            log.info("Received message from "+user.getChatId()+": "+message.getText());
            switch (message.getText()) {
                case "/start":
                    send(user.getChatId(), "Hello, " + user.getFirstName() + "!\n" +
                            "Select currency type for searching", currencies);
                    break;
                case "USD":
                case "EUR":
                case "RUB":
                    userRequests.put(user.getChatId(), message.getText());
                    send(user.getChatId(), "Select an information source ", sources);
                    break;
                case "PrivatBank":
                    try {

                        double[] rates = CurrencyLoader.privat(userRequests.get(user.getChatId()));
                        send(user.getChatId(), "PrivatBank\n" +
                                "SELL: 1 " + userRequests.get(user.getChatId()) + " = " + rates[0] + " UAH\n"
                                + "BUY: 1 " + userRequests.get(user.getChatId()) + " = " + rates[1] + " UAH", null);
                    }
                    catch (NullPointerException e) {
                        send(user.getChatId(), "Select currency type for searching", currencies);
                    }
                    catch (IOException e) {
                        log.warn("IOException during parsing process:");
                        e.printStackTrace();
                    }
                    break;
                case "OschadBank":
                    try {
                        double[] rates = CurrencyLoader.oschad(userRequests.get(user.getChatId()));
                        send(user.getChatId(), "OschadBank\n" +
                                "SELL: 1 " + userRequests.get(user.getChatId()) + " = " + rates[0] + " UAH\n"
                                + "BUY: 1 " + userRequests.get(user.getChatId()) + " = " + rates[1] + " UAH", null);
                    }
                    catch (NullPointerException e) {
                        send(user.getChatId(), "Select currency type for searching", currencies);
                    }
                    catch (IOException e) {
                        log.warn("IOException during parsing process:");
                        e.printStackTrace();
                    }
                    break;
                case "Money24":
                    try {
                        double[] rates = CurrencyLoader.money24(userRequests.get(user.getChatId()));
                        send(user.getChatId(), "Money24\n" +
                                "SELL: 1 " + userRequests.get(user.getChatId()) + " = " + rates[0] + " UAH\n"
                                + "BUY: 1 " + userRequests.get(user.getChatId()) + " = " + rates[1] + " UAH", null);
                    }
                    catch (NullPointerException e) {
                        send(user.getChatId(), "Currency is not selected\nSelect currency type for searching", currencies);
                    }
                    catch (IOException e) {
                        log.warn("IOException during parsing process:");
                        e.printStackTrace();
                    }
                    break;
                case "<-":
                    send(user.getChatId(), "Select currency type for searching", currencies);
                    break;
                default:
                    log.warn("Unexpected massage: "+message.getText());
                    send(user.getChatId(), "Invalid input, please, user forward keyboard", currencies);
                    break;
            }
        }

    }

    public ReplyKeyboardMarkup getKeyboard(String[] keys) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(Arrays.asList(keys));
        keyboardRows.add(keyboardRow);
        keyboard.setKeyboard(keyboardRows);

        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        return keyboard;
    }

    public void send(String chatId, String text, String[] keys) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if(keys != null) {
            sendMessage.setReplyMarkup(getKeyboard(keys));
        }
        try {
            log.info("Message to "+chatId+": "+text);
            sendMessage.setText(text);
            execute(sendMessage);
            log.info("Message sent");
        } catch (TelegramApiException e) {
            log.error("Message not sent");
            e.printStackTrace();
        }

    }

    public void addUser(User user) {
        if(!userRepository.findByChatId(user.getChatId()).isPresent()) {
            userRepository.save(user);
            log.info("Save new user: "+userRepository.findByChatId(user.getChatId()).get());
        }

    }


}
