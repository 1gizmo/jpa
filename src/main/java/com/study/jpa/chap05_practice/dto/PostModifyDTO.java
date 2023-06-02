package com.study.jpa.chap05_practice.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter @Getter
@ToString @EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostModifyDTO {
        @NotBlank
        @Size(min = 1, max = 20)
        private String title;

        private String content;

        @NotBlank
        private Long postNo = 0L;


}
