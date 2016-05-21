package com.mmrath.sapp.web.websocket;

import com.mmrath.sapp.security.SecurityUtils;
import com.mmrath.sapp.web.dto.ActivityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.mmrath.sapp.config.WebsocketConfiguration.IP_ADDRESS;

@Controller
public class ActivityService implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);
    @Autowired
    SimpMessageSendingOperations messagingTemplate;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @SubscribeMapping("/topic/activity")
    @SendTo("/topic/tracker")
    public ActivityDto sendActivity(@Payload ActivityDto activityData, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        activityData.setUserLogin(SecurityUtils.getCurrentUserLogin());
        activityData.setUserLogin(principal.getName());
        activityData.setSessionId(stompHeaderAccessor.getSessionId());
        activityData.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        Instant instant = Instant.ofEpochMilli(Calendar.getInstance().getTimeInMillis());
        activityData.setTime(dateTimeFormatter.format(ZonedDateTime.ofInstant(instant, ZoneOffset.systemDefault())));
        log.debug("Sending user tracking data {}", activityData);
        return activityData;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        ActivityDto activityData = new ActivityDto();
        activityData.setSessionId(event.getSessionId());
        activityData.setPage("logout");
        messagingTemplate.convertAndSend("/topic/tracker", activityData);
    }
}
