package com.bnp.tictactoe.dto;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@ToString
public class PlayResponse {

    private Boolean accepted;
    private Boolean gameOver;
    private String winner;
    private String nextPlayer;
    private Map<String, String> state;
    private String failureMessage;

}
