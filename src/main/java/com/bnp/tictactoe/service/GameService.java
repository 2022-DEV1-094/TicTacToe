package com.bnp.tictactoe.service;

import com.bnp.tictactoe.dto.PlayRequest;
import com.bnp.tictactoe.dto.PlayResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface GameService {

    Mono<PlayResponse> play(@NotNull @Valid PlayRequest playRequest);

    Mono<Void> reset();

}
