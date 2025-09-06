package com.shoppingcart.app.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import exception.RequestException;

public class Request {
    // Private constructor to prevent instantiation
    private Request() {}

    public static StringBuilder sendRequest(String apiUrl) throws MalformedURLException, RequestException {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            return content;
        } catch (Exception e) {
            throw new RequestException("Error: " + e.getMessage(), e);
        }
    }
}
