package backend.mwvb.entity;

import lombok.Data;

import java.util.List;

@Data
public class TheIntro {
    private static final String TITLE = "INTRODUCTION to the Second Edition";
    private final List<String> paragraphs;
}
