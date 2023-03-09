package br.com.carv.clientbeer.client.service;

import br.com.carv.clientbeer.client.dto.request.BeerPostRequest;
import br.com.carv.clientbeer.client.dto.request.BeerPutRequest;
import br.com.carv.clientbeer.client.dto.response.BeerPagedListResponse;
import br.com.carv.clientbeer.client.dto.response.BeerGenericResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BeerClient {

    Mono<BeerGenericResponse> getBeerById(UUID id, Boolean showInventoryOnHand);

    Mono<BeerPagedListResponse> listBears(Integer pageNumber, Integer pageSize, String beerName,
                                          String beerStyle, Boolean showInventoryOnHand);

    Mono<ResponseEntity> createBeer(BeerPostRequest beerPostRequest);

    Mono<ResponseEntity> updateBeer(BeerPutRequest beerPutRequest);

    Mono<ResponseEntity> deleteBeerById(UUID id);

    Mono<BeerGenericResponse> getBeerByUPC(String upc);
}
