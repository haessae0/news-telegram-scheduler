package com.haessae0.newsbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NaverApiService {

    private final String clientId = "YOUR_CLIENT_ID";
    private final String clientSecret = "YOUR_CLIENT_SECRET";

    // 네이버 API 요청 메서드
    public String getRequestUrl(String apiURL) {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            int responseCode = con.getResponseCode();
            if (responseCode == 200) { // 정상 응답
                // UTF-8로 인코딩된 응답을 읽음
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                return response.toString();
            } else {
                log.error("Error Code: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return null;
        }
    }

    // 뉴스 검색 API 호출
    public List<String[]> searchNaverNews(String keyword, int display, int start) throws Exception {
        List<String[]> newsList = new ArrayList<>();
        String apiURL = "https://openapi.naver.com/v1/search/news.json?query="
                + URLEncoder.encode(keyword, StandardCharsets.UTF_8) + "&display=" + display + "&sort=date&start=" + start;


        String response = getRequestUrl(apiURL);

        if (response != null) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode items = rootNode.path("items");

            for (JsonNode item : items) {
                String title = item.path("title").asText()
                        .replace("&quot;", "\"")
                        .replace("&amp;", "&")
                        .replace("&lt;", "<")
                        .replace("&gt;", ">")
                        .replace("<b>", "")
                        .replace("</b>", "");
                String link = item.path("link").asText();

                log.info(title + " >> " + link);

                // 데이터를 저장할 배열
                newsList.add(new String[]{title, link});
            }
        }
        return newsList;
    }

}
