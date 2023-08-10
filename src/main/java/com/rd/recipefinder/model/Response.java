package com.rd.recipefinder.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class Response {
    private List<Hits> hits;
    @Data
    public static class Hits {
        private Recipe recipe;
        @Data
        public static class Recipe {
            private String uri;
            private String label;
            private String image;
            private String source;
            private String url;
            private String yield;
            private List<String> healthLabels;
            private List<String> cautions;
            private List<String> ingredientLines;
            private List<String> cuisineType;
        }
    }
}
