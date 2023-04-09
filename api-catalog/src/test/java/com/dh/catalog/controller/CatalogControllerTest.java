package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;

class CatalogControllerTest {

    @BeforeClass
    public static void setup(){

        RestAssured.baseURI="http:loaclhost:8080";
    }

    @Test
    void createMovie() throws InterruptedException {
        var  movieCreated = given()
                .contentType(ContentType.JSON)
                .when()
                .body(new MovieServiceClient.MovieDto(null,"Moulin Rouge","Musical","www.netflix.com"))
                .post("/api/v1/movies/save")
                .then()
                .statusCode(200)
                .body("name", equalTo("Moulin Rouge"));
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre", "Musical")
                .get("/api/v1/catalog/online/{genre}")
                .then()
                .statusCode(200)
                .body("movies[0].name", equalTo("Moulin Rouge"));

        TimeUnit.SECONDS.sleep(10);

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre", "Musical")
                .get("/api/v1/catalog/offline/{genre}")
                .then()
                .statusCode(200)
                .body("movies[0].name", equalTo("Moulin Rouge"));

    }

    @Test
    void createSerie() throws InterruptedException {
        List<SerieServiceClient.SerieDTO.Season.Chapter> chapter = List.of(new SerieServiceClient.SerieDTO.Season.Chapter("Piloto",3,"www.netflix"));
        List<SerieServiceClient.SerieDTO.Season> season = List.of(new SerieServiceClient.SerieDTO.Season(2,chapter));

        var  serieCreated = given()
                .contentType(ContentType.JSON)
                .when()
                .body(new SerieServiceClient.SerieDTO(null, "Chicago", "musical",season))
                .post("/api/v1/series")
                .then()
                .statusCode(201)
                .body("name", equalTo("Chicago"));
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre", "Musical")
                .get("/api/v1/catalog/online/{genre}")
                .then()
                .statusCode(201)
                .body("series[0].name", equalTo("Chicago"));

        TimeUnit.SECONDS.sleep(10);

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre", "Musical")
                .get("/api/v1/catalog/offline/{genre}")
                .then()
                .statusCode(200)
                .body("series[0].name", equalTo("Moulin Rouge"));

    }

}