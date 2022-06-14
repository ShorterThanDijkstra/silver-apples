package backend.mwvb.entity;

import lombok.Data;

import java.util.List;

public record TheIntro(List<String> paragraphs) {
    private static final String TITLE = "INTRODUCTION to the Second Edition";
}
