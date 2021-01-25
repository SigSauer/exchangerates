package com.sigsauer.exchangerates.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

@Slf4j
public class CurrencyLoader {

    public static double[] privat(String currency) throws IOException {
        log.info("Parsing URL: https://privatbank.ua/ by param: "+currency);
        Document document = Jsoup.connect("https://privatbank.ua/").get();
        Element sell = document.getElementById(currency + "_sell");
        Element buy = document.getElementById(currency + "_buy");

        return new double[]{Double.parseDouble(sell.text()), Double.parseDouble(buy.text())};
    }

    public static double[] oschad(String currency) throws IOException {
        log.info("Parsing URL: https://www.oschadbank.ua/ua by param: "+currency);
        Document document = Jsoup.connect("https://www.oschadbank.ua/ua").get();
        Element sell = document.getElementsByClass("sell-" + currency).first();
        Element buy = document.getElementsByClass("buy-" + currency).first();

        return new double[]{Double.parseDouble(sell.text()), Double.parseDouble(buy.text())};
    }

    public static double[] money24(String currency) throws IOException {
        log.info("Parsing URL: https://money24.kharkov.ua/" + currency.toLowerCase() + "-uah/");
        Document document = Jsoup.connect("https://money24.kharkov.ua/" + currency.toLowerCase() + "-uah/").get();
        Element buy = document.getElementsByClass("rate-number").first();
        Element sell = document.getElementsByClass("rate-number").last();

        return new double[]{Double.parseDouble(sell.text()), Double.parseDouble(buy.text())};
    }

}
