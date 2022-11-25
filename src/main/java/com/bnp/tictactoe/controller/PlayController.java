package com.bnp.tictactoe.controller;

import com.bnp.tictactoe.dto.PlayRequest;
import com.bnp.tictactoe.dto.PlayResponse;
import com.bnp.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping(path = {"/game"})
public class PlayController {

    private final GameService gameService;


    @Autowired
    public PlayController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(path = {"/play"})
    @ResponseBody
    public Mono<ResponseEntity<PlayResponse>> play(@NotNull @Valid @RequestBody PlayRequest playRequest) {
        return gameService.play(playRequest).map(ResponseEntity::ok);
    }

    @PutMapping(path = {"/reset"})
    public Mono<ResponseEntity<Void>> reset() {
        return gameService.reset()
                .map(_Void -> ResponseEntity.ok().build());
    }
}
