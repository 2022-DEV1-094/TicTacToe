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
import java.util.Objects;

/**
 * The main service class providing the game functionality.
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
        PlayResponse playResponse;

        if (theWinner() != null) {
            return Mono.just(PlayResponse.builder()
                    .accepted(false).gameOver(true).winner(theWinner()).state(board)
                    .build());
        }

        if (!gameAlreadyStarted()) {
            if (playRequest.getPlayerId().equals(Constants.PLAYER_X)) {
                // ok, proceed
                if (positionValid(playRequest.getPosition())) {
                    board.put(String.valueOf(playRequest.getPosition() - 1), playRequest.getPlayerId());
                    playerId = Constants.PLAYER_X;
                    playResponse = PlayResponse.builder()
                            .accepted(true).gameOver(false).nextPlayer(Constants.PLAYER_O).state(board)
                            .build();
                } else {
                    playResponse = PlayResponse.builder()
                            .accepted(false).gameOver(false).state(board).nextPlayer(playRequest.getPlayerId()).failureMessage("Invalid position! Please select another one..")
                            .build();
                }
            } else {
                playResponse = PlayResponse.builder()
                        .accepted(false).gameOver(false).state(board).nextPlayer(Constants.PLAYER_X).failureMessage("Sorry! player X must start first..")
                        .build();
            }
        } else {
            if (playRequest.getPlayerId().equals(playerId)) {
                playResponse = PlayResponse.builder()
                        .accepted(false).gameOver(false).state(board).nextPlayer(nextPlayer()).failureMessage("Sorry! It is not your turn..")
                        .build();
            } else {
                if (!positionValid(playRequest.getPosition())) {
                    playResponse = PlayResponse.builder()
                            .accepted(false).gameOver(false).state(board).nextPlayer(playRequest.getPlayerId()).failureMessage("Invalid position! Please select another one..")
                            .build();
                } else {
                    board.put(String.valueOf(playRequest.getPosition() - 1), playRequest.getPlayerId());
                    playerId = playRequest.getPlayerId();

                    // Update the game's finished state
                    var winner = theWinner();

                    playResponse = PlayResponse.builder()
                            .accepted(true).state(board).winner(winner).gameOver(winner != null).nextPlayer((winner != null) ? null : nextPlayer())
                            .build();
                }
            }
        }

        return Mono.just(playResponse);
    }

    @Override
    public Mono<Void> reset() {
        init();
        return Mono.empty().then();
    }

    // ******************************************

    /**
     * Check if there is an entry in the map that has a value != null
     * which means that some player already played its turn.
     *
     * @return - true if there is at least one non-null item in the board, false otherwise.
     */
    private boolean gameAlreadyStarted() {
        for (Map.Entry<String, String> entry : board.entrySet()) {
            if (entry.getValue() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines the next player based on the currently playing one.
     *
     * @return - the next player's ID.
     */
    private String nextPlayer() {
        return playerId.equals(Constants.PLAYER_X) ? Constants.PLAYER_O : Constants.PLAYER_X;
    }

    /**
     * Checks if the position is valid (in the range of [1 - 9])
     * and also can be placed on the board (position is empty).
     *
     * @param position - the position to be played.
     * @return -  true if the position is in range and can be placed on the board, false otherwise.
     */
    private boolean positionValid(Integer position) {
        return position > 0 && position < 10 && board.get(String.valueOf(position - 1)) == null;
    }

    /**
     * Checks who won the game.
     *
     * @return - the player's ID of the winner, draw if no winner or null if the game isn't over yet.
     */
    private String theWinner() {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = board.get("0") + board.get("1") + board.get("2");
                    break;
                case 1:
                    line = board.get("3") + board.get("4") + board.get("5");
                    break;
                case 2:
                    line = board.get("6") + board.get("7") + board.get("8");
                    break;
                case 3:
                    line = board.get("0") + board.get("3") + board.get("6");
                    break;
                case 4:
                    line = board.get("1") + board.get("4") + board.get("7");
                    break;
                case 5:
                    line = board.get("2") + board.get("5") + board.get("8");
                    break;
                case 6:
                    line = board.get("0") + board.get("4") + board.get("8");
                    break;
                case 7:
                    line = board.get("2") + board.get("4") + board.get("6");
                    break;
            }
            // logger.info("Line: {}", line);
            if (line.equals("XXX")) {
                return Constants.PLAYER_X;
            } else if (line.equals("OOO")) {
                return Constants.PLAYER_O;
            }
        }

        if (board.values().stream().filter(Objects::nonNull).count() == 8) {
            return "draw";
        }

        return null;
    }

    @Deprecated(since = "1.0.0", forRemoval = true)
    private boolean boardFull() {
        for (Map.Entry<String, String> entry : board.entrySet()) {
            if (entry.getValue() == null) {
                return false;
            }
        }
        return true;
    }
}
