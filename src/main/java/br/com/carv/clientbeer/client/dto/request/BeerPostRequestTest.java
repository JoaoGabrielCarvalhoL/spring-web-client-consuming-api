package br.com.carv.clientbeer.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class BeerPostRequestTest {

    @NotBlank(message = "The name cannot be empty")
    @Size(max = 100, min = 5, message = "Maximum number of characters for this field is 100.")
    private String beerName;

    private String beerStyle;

    private String upc;

    private String price;

    private Integer quantityOnHand;

    public BeerPostRequestTest() {

   }

    public BeerPostRequestTest(String beerName, String beerStyle, String upc, String price, Integer quantityOnHand) {
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.price = price;
        this.quantityOnHand = quantityOnHand;
    }

    public BeerPostRequestTest(String beerName, String beerStyle, String upc, String price) {
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }
}
