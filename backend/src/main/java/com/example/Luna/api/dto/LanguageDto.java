package com.example.Luna.api.dto;

import com.example.Luna.api.model.Language;
import com.example.Luna.api.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {
    private Long id;
    private String language;

    public LanguageDto(Language language) {
        this.id = language.getId();
        this.language = language.getLanguage();
    }
}
