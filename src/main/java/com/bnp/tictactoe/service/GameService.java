package com.bnp.tictactoe.service;

import com.bnp.tictactoe.dto.PlayRequest;
import com.bnp.tictactoe.dto.PlayResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface GameService {

    /**
     * Provides the functionality of the play operation.
     *
     * @param playRequest - an object containing the playerId and his selected position
     * @return - An instance of PlayResponse which represents the result
     *           of the play operation wrapped in a reactive type for async processing.
     */
    Mono<PlayResponse> play(@NotNull @Valid PlayRequest playRequest);

    /**
     * Resets the game.
     *
     * @return - Void wrapped in a reactive type.
     */
    Mono<Void> reset();

}
