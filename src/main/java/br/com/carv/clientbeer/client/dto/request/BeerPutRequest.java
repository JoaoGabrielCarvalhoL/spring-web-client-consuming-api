package br.com.carv.clientbeer.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public class BeerPutRequest {

    @NotBlank
    private UUID id;

    @NotBlank(message = "The name cannot be empty")
    @Size(max = 100, min = 5, message = "Maximum number of characters for this field is 100.")
    private String beerName;

    private String beerStyle;

    private String upc;

    private BigDecimal price;

    private Integer quantityOnHand;

    public BeerPutRequest() {

   }

    public BeerPutRequest(UUID id, String beerName, String beerStyle, String upc, BigDecimal price, Integer quantityOnHand) {
        this.id = id;
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.price = price;
        this.quantityOnHand = quantityOnHand;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getBeerStyle() {
        return beerStyle;
    }

    public void setBeerStyle(String beerStyle) {
        this.beerStyle = beerStyle;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }
}
