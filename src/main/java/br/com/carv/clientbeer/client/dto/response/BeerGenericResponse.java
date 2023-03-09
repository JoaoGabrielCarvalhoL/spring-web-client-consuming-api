package br.com.carv.clientbeer.client.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BeerGenericResponse {


    private UUID id;
    private String beerName;

    private String beerStyle;

    private String upc;

    private BigDecimal price;

    private Integer quantityOnHand;

    private OffsetDateTime createdDate;

    private OffsetDateTime lastUpdateDate;

    public BeerGenericResponse() {

    }

    public BeerGenericResponse(UUID id, String beerName, String beerStyle, String upc, BigDecimal price,
                               Integer quantityOnHand, OffsetDateTime createdDate, OffsetDateTime lastUpdateDate) {
        this.id = id;
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.price = price;
        this.quantityOnHand = quantityOnHand;
        this.createdDate = createdDate;
        this.lastUpdateDate = lastUpdateDate;
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

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }



    public void setLastUpdateDate(OffsetDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        return "BeerGenericResponse{" +
                "id=" + id +
                ", beerName='" + beerName + '\'' +
                ", beerStyle='" + beerStyle + '\'' +
                ", upc='" + upc + '\'' +
                ", price=" + price +
                ", quantityOnHand=" + quantityOnHand +
                ", createdDate=" + createdDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
