package br.com.carv.clientbeer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(WebClientProperties.BASE_URL).build();
    }

    /**
     * @Bean
     *     public WebClient webClient(){
     *         return WebClient.builder()
     *                 .baseUrl(WebClientProperties.BASE_URL)
     *                 .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
     *                     .wiretap("reactor.netty.client.HttpClient", LogLevel.DEBUG,
     *                             AdvancedByteBufFormat.TEXTUAL)))
     *                 .build();
     *     }
     *
     *     application.properties: logging.level.reactor.netty.http=trace
     *
     *
     *
     */
}
