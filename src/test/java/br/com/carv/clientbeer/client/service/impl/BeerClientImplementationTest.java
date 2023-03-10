package br.com.carv.clientbeer.client.service.impl;

import br.com.carv.clientbeer.client.dto.request.BeerPostRequest;
import br.com.carv.clientbeer.client.dto.request.BeerPutRequest;
import br.com.carv.clientbeer.client.dto.response.BeerGenericResponse;
import br.com.carv.clientbeer.client.dto.response.BeerPagedListResponse;
import br.com.carv.clientbeer.client.exception.ResourceNotFoundFromClientException;
import br.com.carv.clientbeer.config.WebClientConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

class BeerClientImplementationTest {

    private BeerClientImplementation beerClient;

    private final Logger logger = Logger.getLogger(BeerClientImplementationTest.class.getCanonicalName());

    @BeforeEach
    void setUp() {
        this.beerClient = new BeerClientImplementation(new WebClientConfig().webClient());
    }

    @Test
    void getBeerByIdShowInventoryTrue() {

        Mono<BeerPagedListResponse> beerPagedListResponseMono =
                this.beerClient.listBears(null, null, null, null, null);

        BeerPagedListResponse pagedListResponse = beerPagedListResponseMono.block();

        UUID id = pagedListResponse.getContent().get(0).getId();


        Mono<BeerGenericResponse> beerGenericResponseMono =
                this.beerClient.getBeerById(id, true);

        BeerGenericResponse block = beerGenericResponseMono.block();

        Assertions.assertThat(block).isNotNull();
        Assertions.assertThat(block.getId()).isNotNull();
        Assertions.assertThat(id).getClass().equals(UUID.class);
        logger.info("Value ID: " + id);
        logger.info(block.toString());
    }

    @Test
    void functionalGetBeerById() throws InterruptedException {
        AtomicReference<String> beerName = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        beerClient.listBears(null, null, null, null,
                        null)
                .map(beerPagedList -> beerPagedList.getContent().get(0).getId())
                .map(beerId -> beerClient.getBeerById(beerId, false))
                .flatMap(mono -> mono)
                .subscribe(beerDto -> {
                    System.out.println(beerDto.getBeerName());
                    beerName.set(beerDto.getBeerName());
                    Assertions.assertThat(beerDto.getBeerName()).isEqualTo("Mango Bobs");
                    countDownLatch.countDown();
                });

        countDownLatch.await();

        Assertions.assertThat(beerName.get()).isEqualTo("Mango Bobs");
    }

    @Disabled("Problem With API")
    @Test
    void getBeerByIdShowInventoryFalse() {

        Mono<BeerPagedListResponse> beerPagedListResponseMono =
                this.beerClient.listBears(null, null, null, null, null);

        BeerPagedListResponse pagedListResponse = beerPagedListResponseMono.block();

        UUID id = pagedListResponse.getContent().get(0).getId();

        Mono<BeerGenericResponse> beerGenericResponseMono =
                this.beerClient.getBeerById(id, false);

        BeerGenericResponse block = beerGenericResponseMono.block();

        Assertions.assertThat(block).isNotNull();
        Assertions.assertThat(block.getId()).isNotNull();
        Assertions.assertThat(id).getClass().equals(UUID.class);
        logger.info("Value ID: " + id);
        logger.info(block.toString());
    }

    @Test
    void listBearsNoParams() {
        Mono<BeerPagedListResponse> beerPagedListResponseMono =
                this.beerClient.listBears(null, null, null, null, null);
        BeerPagedListResponse pagedListResponse = beerPagedListResponseMono.block();
        Assertions.assertThat(pagedListResponse).isNotNull();
        Assertions.assertThat(pagedListResponse.getContent().size()).isGreaterThan(0);
        pagedListResponse.stream().forEach(System.out::println);
    }

    @Test
    void listBearsPageSize10() {
        Mono<BeerPagedListResponse> beerPagedListResponseMono =
                this.beerClient.listBears(null, 10, null, null, null);
        BeerPagedListResponse pagedListResponse = beerPagedListResponseMono.block();
        Assertions.assertThat(pagedListResponse).isNotNull();
        Assertions.assertThat(pagedListResponse.getContent().size()).isEqualTo(10);
        pagedListResponse.stream().forEach(System.out::println);
    }

    @Test
    void listBearsNoRecords() {
        Mono<BeerPagedListResponse> beerPagedListResponseMono =
                this.beerClient.listBears(10, 20, null, null, null);
        BeerPagedListResponse pagedListResponse = beerPagedListResponseMono.block();
        Assertions.assertThat(pagedListResponse).isNotNull();
        Assertions.assertThat(pagedListResponse.toList()).isEmpty();
    }

    @Test
    void createBeer() {

        BeerPostRequest beerPostRequest = new BeerPostRequest("Dogfishhead 90 Min IPA",
                "IPA", "234848549999", new BigDecimal("10.99"));

        Mono<ResponseEntity<Void>> responseEntityMono = this.beerClient.createBeer(beerPostRequest);

        ResponseEntity responseEntity = responseEntityMono.block();
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        Assertions.assertThat(statusCode).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updateBeer() {

        Mono<BeerPagedListResponse> beerPagedListResponseMono =
                this.beerClient.listBears(null, null, null, null, null);

        BeerPagedListResponse pagedListResponse = beerPagedListResponseMono.block();

        UUID id = pagedListResponse.getContent().get(0).getId();

        BeerPutRequest beerPutRequest = new BeerPutRequest("Dogfishhead 90 Min IPA Updated",
                "IPA", "234848549999", new BigDecimal("10.99"));


        Mono<ResponseEntity<Void>> responseEntityMono = this.beerClient.updateBeer(id, beerPutRequest);
        ResponseEntity<Void> responseEntity = responseEntityMono.block();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }

    @Test
    void deleteBeerById() {

        Mono<BeerPagedListResponse> beerPagedListResponseMono =
                this.beerClient.listBears(null, null, null, null, null);

        BeerPagedListResponse pagedListResponse = beerPagedListResponseMono.block();

        UUID id = pagedListResponse.getContent().get(0).getId();

        Mono<ResponseEntity<Void>> responseEntityMono = this.beerClient.deleteBeerById(id);
        ResponseEntity<Void> responseEntity = responseEntityMono.block();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteBeerByIdNotFound() {

        Mono<ResponseEntity<Void>> responseEntityMono = this.beerClient.deleteBeerById(UUID.randomUUID());

        org.junit.jupiter.api.Assertions.assertThrows(WebClientResponseException.class, () -> {
            ResponseEntity<Void> responseEntity = responseEntityMono.block();
            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        });
    }

    @Test
    void deleteBeerByIdNotFoundHandleException() {

        Mono<ResponseEntity<Void>> responseEntityMono = this.beerClient.deleteBeerById(UUID.randomUUID());

        ResponseEntity<Void> responseEntity = responseEntityMono.onErrorResume(throwable -> {
            if (throwable instanceof WebClientResponseException) {
                WebClientResponseException exception = (WebClientResponseException) throwable;
                return Mono.just(ResponseEntity.status(exception.getStatusCode()).build());
            } else {
                throw new RuntimeException(throwable);
            }
        }).block();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getBeerByUPC() {

        String paramUPC = "0631234200036";

        Mono<BeerGenericResponse> beerGenericResponseMono = this.beerClient.getBeerByUPC(paramUPC);
        BeerGenericResponse beerGenericResponse = beerGenericResponseMono.block();

        Assertions.assertThat(beerGenericResponse).isNotNull();
        Assertions.assertThat(beerGenericResponse.getUpc()).isEqualTo(paramUPC);
    }

    @Test
    void getBeerByIdFromFindAllBears() {

        Mono<BeerPagedListResponse> beerPagedListResponseMono =
                this.beerClient.listBears(null, null, null, null, null);
        BeerPagedListResponse pagedListResponse = beerPagedListResponseMono.block();

        BeerGenericResponse beerGenericResponse = pagedListResponse.stream().filter(beer -> beer.getId().equals(pagedListResponse.getContent().get(0).getId())).findFirst()
                .map(paged -> new BeerGenericResponse(paged.getId(), paged.getBeerName(), paged.getBeerStyle(), paged.getUpc(),
                        paged.getPrice(), paged.getQuantityOnHand(), paged.getCreatedDate(), paged.getLastUpdateDate()))
                .orElseThrow(() -> new ResourceNotFoundFromClientException("Beer not found! Id: " + pagedListResponse.getContent().get(0).getId()));

        Assertions.assertThat(beerGenericResponse).isNotNull();
        Assertions.assertThat(beerGenericResponse.getId()).isNotNull();
        Assertions.assertThat(beerGenericResponse.getId()).isEqualTo(pagedListResponse.getContent().get(0).getId());
        logger.info(beerGenericResponse.toString());


    }

    @Test
    void getBeerByUPCFromFindAllBears() {

        String paramUPC = "0631234200036";

        Mono<BeerPagedListResponse> beerPagedListResponseMono =
                this.beerClient.listBears(null, null, null, null, null);
        BeerPagedListResponse pagedListResponse = beerPagedListResponseMono.block();

        BeerGenericResponse beerGenericResponse = pagedListResponse.stream().filter(beer -> beer.getUpc().equals(paramUPC)).findFirst()
                .map(paged -> new BeerGenericResponse(paged.getId(), paged.getBeerName(), paged.getBeerStyle(), paged.getUpc(),
                        paged.getPrice(), paged.getQuantityOnHand(), paged.getCreatedDate(), paged.getLastUpdateDate()))
                .orElseThrow(() -> new ResourceNotFoundFromClientException("Beer not found! UPC: " + paramUPC));

        Assertions.assertThat(beerGenericResponse).isNotNull();
        Assertions.assertThat(beerGenericResponse.getId()).isNotNull();
        Assertions.assertThat(beerGenericResponse.getUpc()).isEqualTo(paramUPC);
        logger.info(beerGenericResponse.toString());

    }
}