package com.rd.recipefinder.service;

import com.rd.recipefinder.model.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private static final String API_URL = "https://edamam-recipe-search.p.rapidapi.com/search?q=";
    private static final String API_KEY = "2e47353017msh262f776d039505cp1dba8fjsnc566c82373af";
    private static final String API_HOST = "edamam-recipe-search.p.rapidapi.com";
    public List<Response.Hits.Recipe> searchRecipes(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = API_URL + encodedQuery;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
           request.getHeaders().add("X-RapidAPI-Key", API_KEY);
           request.getHeaders().add("X-RapidAPI-Host", API_HOST);
           return execution.execute(request, body);
        });

        Response response = restTemplate.getForObject(url, Response.class);

        if (response != null) {
            List<Response.Hits.Recipe> recipes = new ArrayList<>();
            for (Response.Hits hit : response.getHits()) {
                Response.Hits.Recipe recipe = hit.getRecipe();
                recipes.add(recipe);
            }
            return recipes;
        } else {
            return Collections.emptyList();
        }
    }
}
