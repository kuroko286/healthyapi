package com.kuroko.heathyapi.feature.chatgpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.client.RestTemplate;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.user.User;

@Controller
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class ChatController {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/v1/messages")
    public ResponseEntity<List<Message>> getMessages(@RequestAttribute("email") String email) {
        return ResponseEntity.ok(messageService.getAllMessages(email));
    }

    @MessageMapping("/chatgpt")
    public String chat(@Payload Prompt prompt) {
        // create message from prompt
        String email = prompt.getEmail();
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        Message quest = Message.builder().role(Role.USER).content(prompt.getText()).user(user).build();
        messageService.createMessage(quest);
        messagingTemplate.convertAndSendToUser(email, "/user/private", quest);
        // create a request
        ChatRequest request = new ChatRequest(model, prompt.getText());

        // call the chatgpt API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
        String res;
        if (response == null || response.getChoices() == null ||
                response.getChoices().isEmpty()) {
            res = "No response";
        } else {
            // get the first response
            res = response.getChoices().get(0).getMessage().getContent();
        }
        Message answer = Message.builder().role(Role.ASSISTANT).content(res).user(user).build();
        messageService.createMessage(answer);
        messagingTemplate.convertAndSendToUser(email, "/user/private", answer);

        return "Sended message";

    }
}