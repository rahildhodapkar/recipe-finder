package com.rd.recipefinder.controller;

import com.rd.recipefinder.model.Response;
import com.rd.recipefinder.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/recipeSearch")
    public String recipeSearch() { return "recipeSearch"; }

    @PostMapping("/recipeSearch")
    @ResponseBody
    public ResponseEntity<List<Response.Hits.Recipe>> handleRecipeSearch(@RequestParam(name = "field[]") String[] fields) {
        StringBuilder query = new StringBuilder();
        for (String field : fields) {
            query.append(field).append(" ");
        }
        if (query.length() > 0) {
            query.deleteCharAt(query.length() - 1);
        }
        List<Response.Hits.Recipe> recipes = getRecipes(query.toString());
        return ResponseEntity.ok(recipes);
    }


    private List<Response.Hits.Recipe> getRecipes(String query) {
        return recipeService.searchRecipes(query);
    }

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
}
