package br.com.carv.clientbeer.client.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class BeerPagedListResponse extends PageImpl<BeerGenericResponse>  implements Serializable {

    static final long serialVersionUID = 1114715135625836949L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerPagedListResponse(@JsonProperty("content") List<BeerGenericResponse> content,
                         @JsonProperty("number") int number,
                         @JsonProperty("size") int size,
                         @JsonProperty("totalElements") Long totalElements,
                         @JsonProperty("pageable") JsonNode pageable,
                         @JsonProperty("last") boolean last,
                         @JsonProperty("totalPages") int totalPages,
                         @JsonProperty("sort") JsonNode sort,
                         @JsonProperty("first") boolean first,
                         @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public BeerPagedListResponse(List<BeerGenericResponse> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerPagedListResponse(List<BeerGenericResponse> content) {
        super(content);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
