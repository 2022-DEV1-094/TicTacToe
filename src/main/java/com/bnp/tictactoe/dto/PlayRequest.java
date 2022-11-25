package com.bnp.tictactoe.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayRequest {

    @NotBlank(message = "PlayerId must be not null nor empty")
    @Pattern(regexp = "[XO]", message = "PlayerId must be X or O")
    private String playerId;

    @NotNull(message = "Position must not be null")
    @Min(value = 1L, message = "Position must be bigger than 0")
    @Max(value = 9L, message = "Position must be less than 10")
    private Integer position;
}
