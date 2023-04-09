package com.dh.catalog.client;

import com.dh.catalog.config.LoadBalanceConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="serie-service")
@LoadBalancerClient(value="serie-service",configuration = LoadBalanceConfiguration.class)
public interface SerieServiceClient {

    @GetMapping("/api/v1/series/{genre}")
    List<SerieDTO> getSerieByGenre(@PathVariable (value = "genre") String genre);

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Document("Series")
    class SerieDTO{
        @Id
        private String id;
        private String name;
        private String genre;
        private List<Season> seasons = new ArrayList<>();


        @AllArgsConstructor
        @NoArgsConstructor
        @Setter
        @Getter
        public static class Season {

            private Integer seasonNumber;
            private List<Chapter> chapters = new ArrayList<>();

            @AllArgsConstructor
            @NoArgsConstructor
            @Setter
            @Getter
            public static class Chapter {

                private String name;
                private Integer number;
                private String urlStream;


            }

        }

    }
}
