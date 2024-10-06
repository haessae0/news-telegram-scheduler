package com.haessae0.newsbot;

import com.haessae0.newsbot.service.NaverApiService;
import com.haessae0.newsbot.service.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class Scheduler {

    @Autowired
    private TelegramService telegramService;

    @Autowired
    private NaverApiService naverApiService;

    // 매일 8시 12시 16시 21시에 스케줄러 실행
    @Scheduled(cron = "0 0 8,12,16,21 * * ?")
    public void scheduledTask() throws Exception {

        String startTtime = nowDateTime();
        log.info(">>>=========== " + startTtime + " 뉴스 알림 봇 배치를 수행합니다. ===========<<<");
        workingNaver2Telegram("정치");
//        workingNaver2Telegram("IT");
//        workingNaver2Telegram("경제");
        String endTime = nowDateTime();
        log.info(">>>=========== " + endTime + " 뉴스 알림 봇 배치를 종료합니다. ===========<<<");
    }

    private void workingNaver2Telegram(String keyword) throws Exception {
        // 1. 네이버 검색 API 호출하여 링크 리스트 생성
        log.info(">>>=========== 키워드 : " + keyword + " ===========<<<");
        List<String[]> urlList = naverApiService.searchNaverNews(keyword, 5, 1);

        // 2. 메시지 작성
        String message = setMessage(urlList, keyword);

        // 3. 텔레그램 메시지 발송
        telegramService.sendMessage(message);
    }

    private String setMessage(List<String[]> urlList, String keyword) {
        StringBuilder message = new StringBuilder();
        message.append("날짜: ").append(nowDateTime()).append("\n\n");
        message.append("주제: ").append(keyword).append("\n\n");
        for (String[] news : urlList) {
            message.append("제목 : ").append(news[0]).append("\n");
            message.append("뉴스 기사 링크 : ").append(news[1]).append("\n\n\n");
        }

        return message.toString();
    }

    private String nowDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");

        return now.format(formatter);
    }
}
