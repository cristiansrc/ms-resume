package com.cristiansrc.resume.msresume.infrastructure.client.telegram;

import com.cristiansrc.resume.msresume.application.port.output.client.ITelegramClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramClient implements ITelegramClient {

    private final RestTemplate restTemplate;

    @Value("${config.telegram.bot.token}")
    private String botToken;

    @Value("${config.telegram.chat.id}")
    private String chatId;

    private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot{token}/sendMessage";

    @Override
    public void sendMessage(String message) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(TELEGRAM_API_URL)
                    .buildAndExpand(botToken)
                    .toUriString();

            var request = new TelegramMessageRequest(chatId, message);
            restTemplate.postForObject(url, request, String.class);
            log.info("Message sent to Telegram successfully");
        } catch (Exception e) {
            log.error("Error sending message to Telegram", e);
        }
    }

    private record TelegramMessageRequest(String chat_id, String text) {}
}
