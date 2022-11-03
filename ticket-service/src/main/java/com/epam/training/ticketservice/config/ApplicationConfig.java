package com.epam.training.ticketservice.config;

import com.epam.training.ticketservice.util.session.Session;
import com.epam.training.ticketservice.util.session.impl.SessionImpl;
import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
@ComponentScan("com.epam.training.ticketservice")
public class ApplicationConfig implements PromptProvider {

    @Override
    public final AttributedString getPrompt() {
        return new AttributedString("Ticket service>");
    }

    @Bean
    Session session() {
        return new SessionImpl();
    }
}
