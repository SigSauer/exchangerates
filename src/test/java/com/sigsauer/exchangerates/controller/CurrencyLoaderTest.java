package com.sigsauer.exchangerates.controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyLoaderTest {

    //tested at 26/01/2021
    @Test
    void privatUSDTest() {
        String currency = "USD";
        double[] received = new double[2];
        double[] expected = new double[] {28.3, 27.9};
        try {
            received = CurrencyLoader.privat(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,received);
    }

    @Test
    void privatEURTest() {
        String currency = "EUR";
        double[] received = new double[2];
        double[] expected = new double[] {34.4, 33.8};
        try {
            received = CurrencyLoader.privat(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,received);
    }

    @Test
    void privatRUBTest() {
        String currency = "RUB";
        double[] received = new double[2];
        double[] expected = new double[] {0.4, 0.365};
        try {
            received = CurrencyLoader.privat(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,received);
    }

    @Test
    void oschadUSDTest() {
        String currency = "USD";
        double[] received = new double[2];
        double[] expected = new double[] {28.38, 28.0};
        try {
            received = CurrencyLoader.oschad(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,received);
    }

    @Test
    void oschadEURTest() {
        String currency = "EUR";
        double[] received = new double[2];
        double[] expected = new double[] {34.38, 33.68};
        try {
            received = CurrencyLoader.oschad(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,received);
    }

    @Test
    void oschadRUBTest() {
        String currency = "RUB";
        double[] received = new double[2];
        double[] expected = new double[] {0.39, 0.25};
        try {
            received = CurrencyLoader.oschad(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,received);
    }

    @Test
    void money24USDTest() {
        String currency = "USD";
        double[] received = new double[2];
        double[] expected = new double[] {28.3, 28.15};
        try {
            received = CurrencyLoader.money24(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,received);
    }

    @Test
    void money24EURTest() {
        String currency = "EUR";
        double[] received = new double[2];
        double[] expected = new double[] {34.2, 34.0};
        try {
            received = CurrencyLoader.money24(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,received);
    }

    @Test
    void money24RUBTest() {
        String currency = "RUB";
        double[] received = new double[2];
        double[] expected = new double[] {0.376, 0.369};
        try {
            received = CurrencyLoader.money24(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,received);
    }
}
