package br.com.carv.clientbeer.client.service.impl;

import br.com.carv.clientbeer.client.dto.response.BeerGenericResponse;
import br.com.carv.clientbeer.client.dto.response.BeerPagedListResponse;
import br.com.carv.clientbeer.client.exception.ResourceNotFoundFromClientException;
import br.com.carv.clientbeer.config.WebClientConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.UUID;
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
    }

    @Test
    void updateBeer() {
    }

    @Test
    void deleteBeerById() {
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