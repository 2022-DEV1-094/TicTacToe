package com.bnp.tictactoe.service;

import com.bnp.tictactoe.dto.PlayRequest;
import com.bnp.tictactoe.dto.PlayResponse;
import com.bnp.tictactoe.utils.Constants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * todo
 */
@Validated
@Service
public class GameServiceImpl implements GameService {

    private final Logger logger;

    private String playerId;
    private Map<String, String> board;

    // ******************************************

    @Autowired
    public GameServiceImpl(Logger logger) {
        this.logger = logger;

        this.board = new HashMap<>();

        init();
    }

    private void init() {
        this.board.put("0", null);
        this.board.put("1", null);
        this.board.put("2", null);
        this.board.put("3", null);
        this.board.put("4", null);
        this.board.put("5", null);
        this.board.put("6", null);
        this.board.put("7", null);
        this.board.put("8", null);

        this.playerId = Constants.PLAYER_X;
    }

    // ******************************************

    @Override
    public Mono<PlayResponse> play(@NotNull @Valid PlayRequest playRequest) {
        return null;
    }

    @Override
    public Mono<Void> reset() {
        return null;
    }
}
