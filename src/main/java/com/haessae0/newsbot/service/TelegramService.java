package com.haessae0.newsbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class TelegramService {

    private static final String API_TOKEN = "YOU_API_TOKEN";
    private static final String CHAT_ID = "YOUR_CHAT_ID_OR_GROUP_ID";

    public void sendMessage(String message) {
        try {
            String urlString = "https://api.telegram.org/bot" + API_TOKEN + "/sendMessage";
            URL url = new URL(urlString);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.setDoOutput(true);

            String parameters = "chat_id=" + CHAT_ID + "&text=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = parameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                log.info("텔레그램 메시지 전송 성공!");
            } else {
                log.info("텔레그램 메시지 전송 실패! 응답 코드: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
