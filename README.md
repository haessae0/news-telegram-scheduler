# News Telegram Scheduler

Logic that we are sending to one person. You can implement multiple shipments or additional functions through modification. 

한 명에게 발송하고 있는 로직입니다. 수정을 통해 다중 발송 또는 추가 기능을 구현 하시면 될 것 같습니다.

A Spring Boot project that receives news articles through Naver Search API and sends daily updates via a Telegram bot.

스프링 부트 프로젝트로, 네이버 검색 API를 통해 뉴스 기사를 수집하고 텔레그램 봇을 통해 매일 업데이트를 전송합니다.

## Version Information / 버전 정보

- **Spring Boot**: 3.3.4
- **Java**: 17
- **Dependency Management**: 1.1.6

## Dependencies / 의존성

- `spring-boot-starter-web`
- `lombok`

---

## Naver API Configuration

To use the Naver Search API, you must provide your own API credentials. Follow the steps below:

1. Create an application at [Naver Developers](https://developers.naver.com/products/service-api/datalab/datalab.md) to
   get your `Client ID` and `Client Secret`.
2. Replace the placeholders in `NaverApiService.java` with your own credentials.

```java
public class NaverApiService {

    private final String clientId = "YOUR_CLIENT_ID";
    private final String clientSecret = "YOUR_CLIENT_SECRET";
```

## Telegram Bot Configuration

To send messages via the Telegram bot, you need to configure your API token and chat ID. Follow the steps below:

1. Create a new bot using [BotFather](https://t.me/BotFather) on Telegram to get your `API Token`.
2. Replace the placeholders in `TelegramService.java` with your own credentials.

```java
   public class TelegramService {

    private static final String API_TOKEN = "YOUR_API_TOKEN";
    private static final String CHAT_ID = "YOUR_CHAT_ID_OR_GROUP_ID";
```

3. Find your chat ID by messaging your bot or group. You can use the /getUpdates method from Telegram's API to get the
   chat ID if needed.

---

## 네이버 API 설정

네이버 검색 API를 사용하려면, 본인의 API 자격 증명을 제공해야 합니다. 아래 단계에 따라 설정하세요:

1. [Naver Developers](https://developers.naver.com/products/service-api/datalab/datalab.md)에서 애플리케이션을 생성하여 `Client ID`
   와 `Client Secret`을 발급받으세요.
2. `NaverApiService.java` 파일에서 아래 부분을 본인의 자격 증명으로 변경하세요:

```java
public class NaverApiService {

    private final String clientId = "YOUR_CLIENT_ID";
    private final String clientSecret = "YOUR_CLIENT_SECRET";
```

## 텔레그램 봇 설정

텔레그램 봇을 통해 메시지를 보내려면 API 토큰과 채팅 ID를 설정해야 합니다. 아래 단계를 따라 설정하세요:

1. [BotFather](https://t.me/BotFather)를 사용해 새로운 텔레그램 봇을 생성하고 `API Token`을 발급받으세요.
2. `TelegramService.java` 파일에서 아래 부분을 본인의 자격 증명으로 변경하세요:
3.

```java
   public class TelegramService {

    private static final String API_TOKEN = "YOUR_API_TOKEN";
    private static final String CHAT_ID = "YOUR_CHAT_ID_OR_GROUP_ID";
```

3. 텔레그램 봇 또는 그룹에 메시지를 보내 채팅 ID를 확인하세요. 필요한 경우 텔레그램 API의 /getUpdates 메서드를 사용해 채팅 ID를 확인할 