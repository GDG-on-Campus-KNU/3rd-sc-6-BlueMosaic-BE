package com.gdsc.knu.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoogleAiAnalysisRequestDto {
    private List<Content> contents;
    private GenerationConfig generationConfig;
    private List<SafetySetting> safetySettings;

    public GoogleAiAnalysisRequestDto(String prompt, String base64Image) {
        this.contents = List.of(new Content(List.of(
                new Content.Part(prompt)
                , new Content.Part(new Content.Part.InlineData("image/jpeg", base64Image))
                , new Content.Part("\n\nResult : ")
        )));
        this.generationConfig = new GenerationConfig();
        this.safetySettings = List.of(
                new SafetySetting("HARM_CATEGORY_HARASSMENT", "BLOCK_MEDIUM_AND_ABOVE"),
                new SafetySetting("HARM_CATEGORY_HATE_SPEECH", "BLOCK_MEDIUM_AND_ABOVE"),
                new SafetySetting("HARM_CATEGORY_SEXUALLY_EXPLICIT", "BLOCK_MEDIUM_AND_ABOVE"),
                new SafetySetting("HARM_CATEGORY_DANGEROUS_CONTENT", "BLOCK_MEDIUM_AND_ABOVE")
        );
    }

    @AllArgsConstructor
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class Content {
        private List<Part> parts;

        @Getter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Part {
            private String text;
            private InlineData inlineData;

            public Part(String text) {
                this.text = text;
            }

            public Part(InlineData inlineData) {
                this.inlineData = inlineData;
            }

            @AllArgsConstructor
            @Getter
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class InlineData {
                private String mimeType;
                private String data;
            }
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GenerationConfig {
        private double temperature;
        private int topK;
        private int topP;
        private int maxOutputTokens;
        private List<String> stopSequences;

        public GenerationConfig() {
            this.temperature = 0.4;
            this.topK = 32;
            this.topP = 1;
            this.maxOutputTokens = 4096;
            this.stopSequences = List.of();
        }
    }

    @AllArgsConstructor
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class SafetySetting {
        private String category;
        private String threshold;
    }
}
