/*
 * Copyright Clement Levallois 2021-2023. License Attribution 4.0 Intertnational (CC BY 4.0)
 */
package net.clementlevallois.bibliocoupling.controller;

import io.mikael.urlbuilder.UrlBuilder;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author LEVALLOIS
 */
public class BiblioCoupling {

    public static void main(String[] args) {
        System.out.println("Hello World!");

    }

    public String getCommaSeparatedCitedRefsForOneWorkViaTitle(String title) {
        String titleSanitized = title.replaceAll(":", " ");
        StringBuilder sb = new StringBuilder();
        HttpClient client = HttpClient.newHttpClient();
        URI uri = UrlBuilder
                .empty()
                .withScheme("https")
                .withHost("api.openalex.org")
                .withPath("works")
                .addParameter("filter", "title.search:" + titleSanitized)
                .addParameter("select", "id,display_name,referenced_works")
                .addParameter("mailto", "analysis@exploreyourdata.com")
                .toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String bodyString = response.body();
            if (response.statusCode() == 200) {
                try (JsonReader reader = Json.createReader(new StringReader(bodyString))) {
                    JsonObject jsonObjectReturned = reader.readObject();
                    JsonArray resultFields = jsonObjectReturned.getJsonArray("results");
                    if (!resultFields.isEmpty()) {
                        JsonObject citedRefsObject = resultFields.getJsonObject(0);
                        JsonArray citedRefs = citedRefsObject.getJsonArray("referenced_works");
                        for (int i = 0; i < citedRefs.size(); i++) {
                            String citedRef = citedRefs.getString(i);
                            sb.append(citedRef);
                            sb.append(",");
                        }
                    } else {
                        System.out.println("title not found:");
                        System.out.println(title);
                    }
                }
            } else {
                System.out.println("error when retrieving work");
                System.out.println(bodyString);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception while connecting to OpenAlex: " + e.getMessage());
            System.out.println("returning empty string for this call");
        }

        return sb.toString();

    }

    public String getCommaSeparatedCitedRefsForOneWorkViaDOI(String doi) {
        if (!doi.startsWith("http")) {
            if (doi.startsWith("doi.org")) {
                doi = "https://" + doi;
            } else {
                doi = "https://doi.org/" + doi;
            }
        }
        StringBuilder sb = new StringBuilder();
        HttpClient client = HttpClient.newHttpClient();
        URI uri = UrlBuilder
                .empty()
                .withScheme("https")
                .withHost("api.openalex.org")
                .withPath("works/" + doi)
                .addParameter("select", "id,display_name,referenced_works")
                .addParameter("mailto", "analysis@exploreyourdata.com")
                .toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String bodyString = response.body();
            if (response.statusCode() == 200) {
                try (JsonReader reader = Json.createReader(new StringReader(bodyString))) {
                    JsonObject jsonObjectReturned = reader.readObject();
                    JsonArray citedRefs = jsonObjectReturned.getJsonArray("referenced_works");
                    if (citedRefs != null && !citedRefs.isEmpty()) {
                        for (int i = 0; i < citedRefs.size(); i++) {
                            String citedRef = citedRefs.getString(i);
                            sb.append(citedRef);
                            sb.append(",");
                        }
                    }else {
                        System.out.println("doi not found:");
                        System.out.println(doi);
                    }
                }
            } else {
                System.out.println("error when retrieving work");
                System.out.println(bodyString);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception while connecting to OpenAlex: " + e.getMessage());
            System.out.println("returning empty string for this call");
        }

        return sb.toString();

    }
}
