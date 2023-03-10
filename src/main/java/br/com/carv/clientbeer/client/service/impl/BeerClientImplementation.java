package br.com.carv.clientbeer.client.service.impl;

import br.com.carv.clientbeer.client.dto.request.BeerPostRequest;
import br.com.carv.clientbeer.client.dto.request.BeerPutRequest;
import br.com.carv.clientbeer.client.dto.response.BeerGenericResponse;
import br.com.carv.clientbeer.client.dto.response.BeerPagedListResponse;
import br.com.carv.clientbeer.client.service.BeerClient;
import br.com.carv.clientbeer.config.WebClientProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class BeerClientImplementation implements BeerClient {

    private final WebClient webClient;

    private final Logger logger = Logger.getLogger(BeerClientImplementation.class.getSimpleName());

    public BeerClientImplementation(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<BeerGenericResponse> getBeerById(UUID id, Boolean showInventoryOnHand) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
                        .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                        .build(id))
                .retrieve()
                .bodyToMono(BeerGenericResponse.class);
    }

    @Override
    public Mono<BeerPagedListResponse> listBears(Integer pageNumber, Integer pageSize, String beerName,
                                                 String beerStyle, Boolean showInventoryOnHand) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH)
                        .queryParamIfPresent("pageNumber", Optional.ofNullable(pageNumber))
                        .queryParamIfPresent("pageSize", Optional.ofNullable(pageSize))
                        .queryParamIfPresent("beerName", Optional.ofNullable(beerName))
                        .queryParamIfPresent("beerStyle", Optional.ofNullable(beerStyle))
                        .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand)).build())
                .retrieve()
                .bodyToMono(BeerPagedListResponse.class);
    }

    @Override
    public Mono<ResponseEntity<Void>> createBeer(BeerPostRequest beerPostRequest) {
        return this.webClient.post()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH).build())
                .body(BodyInserters.fromValue(beerPostRequest))
                .retrieve()
                .toBodilessEntity();

    }

    @Override
    public Mono<ResponseEntity<Void>> updateBeer(UUID id, BeerPutRequest beerPutRequest) {
        return this.webClient.put()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_PUT).build(id))
                .body(BodyInserters.fromValue(beerPutRequest))
                .retrieve()
                .toBodilessEntity();
    }


    @Override
    public Mono<ResponseEntity<Void>> deleteBeerById(UUID id) {
        return this.webClient.delete()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_DELETE).build(id))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Mono<BeerGenericResponse> getBeerByUPC(String upc) {
        return webClient.get().uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_UPC_PATH)
                .build(upc))
                .retrieve()
                .bodyToMono(BeerGenericResponse.class);

    }
}
