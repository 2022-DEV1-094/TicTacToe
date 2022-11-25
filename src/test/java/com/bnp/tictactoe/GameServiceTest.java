package com.bnp.tictactoe;


import com.bnp.tictactoe.dto.PlayRequest;
import com.bnp.tictactoe.dto.PlayResponse;
import com.bnp.tictactoe.service.GameServiceImpl;
import com.bnp.tictactoe.utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

// @ContextConfiguration(classes = {GameServiceImpl.class})
// @Import({LoggerConfig.class, ValidationAutoConfiguration.class})
@SpringBootTest
public class GameServiceTest {

    @Autowired
    GameServiceImpl gameService;

    @BeforeEach
    public void beforeEach() {
        /*
         Must do this to avoid preserving the state
         of the HashMap which leads to inconsistent tests.
         */
        gameService.reset().block();
    }

    @Test
    public void player_x_should_go_first() {
        var playRequest = new PlayRequest(Constants.PLAYER_O, 1);

        PlayResponse playResponse = gameService.play(playRequest).block(Duration.ofSeconds(1));

        Assertions.assertNotNull(playResponse);
        Assertions.assertFalse(playResponse.getAccepted());
        Assertions.assertEquals("Sorry! player X must start first..", playResponse.getFailureMessage());
    }

    @Test
    public void player_cannot_play_on_played_position() {
        // ************* Player 1 plays
        var playRequest = new PlayRequest(Constants.PLAYER_X, 1);

        PlayResponse playResponse = gameService.play(playRequest).block(Duration.ofMillis(200));

        Assertions.assertNotNull(playResponse);
        Assertions.assertTrue(playResponse.getAccepted());
        Assertions.assertEquals(Constants.PLAYER_O, playResponse.getNextPlayer());

        // ************* Player 2 plays

        playRequest.setPlayerId(Constants.PLAYER_O);

        PlayResponse playResponseO = gameService.play(playRequest).block(Duration.ofMillis(200));

        Assertions.assertNotNull(playResponseO);
        Assertions.assertFalse(playResponseO.getAccepted());
        Assertions.assertEquals(playRequest.getPlayerId(), playResponseO.getNextPlayer());
        Assertions.assertEquals("Invalid position! Please select another one..", playResponseO.getFailureMessage());
    }

    @Test
    public void players_should_alternate_turns() {
        // ************* Player 1 plays
        var playRequest = new PlayRequest(Constants.PLAYER_X, 5);

        PlayResponse playResponse = gameService.play(playRequest).block(Duration.ofMillis(200));

        Assertions.assertNotNull(playResponse);
        Assertions.assertTrue(playResponse.getAccepted());
        Assertions.assertEquals(Constants.PLAYER_O, playResponse.getNextPlayer());

        // ************* Player 2 repeats immediately and takes other player's turn

        playRequest.setPosition(3);

        PlayResponse playResponse2 = gameService.play(playRequest).block(Duration.ofMillis(200));

        Assertions.assertNotNull(playResponse2);
        Assertions.assertFalse(playResponse2.getAccepted());
        Assertions.assertEquals("Sorry! It is not your turn..", playResponse2.getFailureMessage());
    }

    @Test
    public void player_should_win_when_he_gets_three_in_line() {
        // PlayerX selects position 1
        var playRequest1 = new PlayRequest(Constants.PLAYER_X, 1);
        PlayResponse playResponse1 = gameService.play(playRequest1).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse1);
        Assertions.assertFalse(playResponse1.getGameOver());

        var playRequest2 = new PlayRequest(Constants.PLAYER_O, 2);
        PlayResponse playResponse2 = gameService.play(playRequest2).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse2);
        Assertions.assertFalse(playResponse2.getGameOver());

        // PlayerX selects position 4
        var playRequest3 = new PlayRequest(Constants.PLAYER_X, 4);
        PlayResponse playResponse3 = gameService.play(playRequest3).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse3);
        Assertions.assertFalse(playResponse3.getGameOver());

        var playRequest4 = new PlayRequest(Constants.PLAYER_O, 5);
        PlayResponse playResponse4 = gameService.play(playRequest4).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse4);
        Assertions.assertFalse(playResponse4.getGameOver());

        // PlayerX selects position 7, now he has 3 in a row, so he should win
        var playRequest5 = new PlayRequest(Constants.PLAYER_X, 7);
        PlayResponse playResponse5 = gameService.play(playRequest5).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse5);
        Assertions.assertTrue(playResponse5.getGameOver());
        Assertions.assertEquals(Constants.PLAYER_X, playResponse5.getWinner());
    }

    @Disabled
    @Test
    public void game_is_a_draw_when_all_squares_filled_and_no_winner() {
        var playRequest1 = new PlayRequest(Constants.PLAYER_X, 1);
        PlayResponse playResponse1 = gameService.play(playRequest1).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse1);
        Assertions.assertFalse(playResponse1.getGameOver());

        var playRequest2 = new PlayRequest(Constants.PLAYER_O, 2);
        PlayResponse playResponse2 = gameService.play(playRequest2).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse2);
        Assertions.assertFalse(playResponse2.getGameOver());

        var playRequest3 = new PlayRequest(Constants.PLAYER_X, 3);
        PlayResponse playResponse3 = gameService.play(playRequest3).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse3);
        Assertions.assertFalse(playResponse3.getGameOver());

        var playRequest4 = new PlayRequest(Constants.PLAYER_O, 4);
        PlayResponse playResponse4 = gameService.play(playRequest4).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse4);
        Assertions.assertFalse(playResponse4.getGameOver());

        var playRequest5 = new PlayRequest(Constants.PLAYER_X, 5);
        PlayResponse playResponse5 = gameService.play(playRequest5).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse5);
        Assertions.assertFalse(playResponse5.getGameOver());

        var playRequest6 = new PlayRequest(Constants.PLAYER_O, 7);
        PlayResponse playResponse6 = gameService.play(playRequest6).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse6);
        Assertions.assertFalse(playResponse6.getGameOver());

        var playRequest7 = new PlayRequest(Constants.PLAYER_X, 6);
        PlayResponse playResponse7 = gameService.play(playRequest7).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse7);
        Assertions.assertFalse(playResponse7.getGameOver());

        var playRequest8 = new PlayRequest(Constants.PLAYER_O, 9);
        PlayResponse playResponse8 = gameService.play(playRequest8).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse8);
        Assertions.assertFalse(playResponse8.getGameOver());

        var playRequest9 = new PlayRequest(Constants.PLAYER_X, 8);
        PlayResponse playResponse9 = gameService.play(playRequest9).block(Duration.ofMillis(200));
        Assertions.assertNotNull(playResponse9);
        Assertions.assertTrue(playResponse9.getGameOver());
        Assertions.assertEquals("draw", playResponse9.getWinner());
    }

}
