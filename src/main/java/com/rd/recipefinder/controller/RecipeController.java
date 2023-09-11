package com.rd.recipefinder.controller;

import com.rd.recipefinder.model.Response;
import com.rd.recipefinder.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@SessionAttributes("recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/recipeSearch")
    public String recipeSearch() { return "/recipeSearch"; }

    @GetMapping("/recipeResults")
    public String recipeResults(Model model, @ModelAttribute("recipes") List<Response.Hits.Recipe> recipes) {
        model.addAttribute("recipes", recipes);
        return "/recipeResults";
    }

    @PostMapping("/recipeSearch")
    public ModelAndView handleRecipeSearch(@RequestParam(name = "field[]") String[] fields, Model model) {
        StringBuilder query = new StringBuilder();
        for (String field : fields) {
            query.append(field).append(" ");
        }
        query.deleteCharAt(query.length() - 1);
        List<Response.Hits.Recipe> recipes = getRecipes(query.toString());
        ModelAndView modelAndView = new ModelAndView("redirect:/recipeResults");
        model.addAttribute("recipes", recipes);
        return modelAndView;
    }

    private List<Response.Hits.Recipe> getRecipes(String query) {
        return recipeService.searchRecipes(query);
    }

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
}
