package com.prep.account.utilities.network;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EmailSender {

    private final WebClient webClient;

    public EmailSender(){
        webClient = WebClient.create("https://integration3666.azurewebsites.net");
    }

    public void send(String to, String subject, String message){
        Email email = new Email(to, subject, message);
        attemptSend(email);
    }

    private void attemptSend(Email email) {
        webClient.post()
                .uri("/v1/email/send")
                .bodyValue(email)
                .retrieve()
                .bodyToMono(String.class)
                .filter(result -> result != null && result.equals("Email sent."))
                .switchIfEmpty(Mono.just("Email not sent."));
    }
}

@AllArgsConstructor
class Email{
    private String to, subject, message;
}
