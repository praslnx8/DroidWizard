package com.prasilabs.droidwizard;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by Contus team on 11/7/17.
 */

public class TicTacToeRunner {

    public static void main(String[] args) {

        TicTacToe ticTacToe = new TicTacToe(null);
        ticTacToe.computeTicTacToe();

        Scanner scanner = new Scanner(System.in);

        XY winner = ticTacToe.getWinner();

        System.out.println("Welcome to tictactoe");
        while (winner == null && !ticTacToe.getAfterResults().isEmpty()) {
            ticTacToe = getNextMove(ticTacToe);
            ticTacToe.print();
            winner = ticTacToe.getWinner();
            if(winner != null) {
                System.out.println("Winner is : " + winner.toString());
            }
            int xPos = scanner.nextInt();
            int yPos = scanner.nextInt();
            ticTacToe.getBoard()[xPos][yPos] = XY.Y;
            for(TicTacToe next : ticTacToe.getAfterResults()) {
                if(next.isSame(ticTacToe.getBoard())) {
                    ticTacToe = next;
                    break;
                }
            }
            ticTacToe.print();
            winner = ticTacToe.getWinner();
            if(winner != null) {
                System.out.println("Winner is : " + winner.toString());
            }
        }
    }

    public static TicTacToe getNextMove(TicTacToe current) {

        TicTacToe yNotPossible = null;
        TicTacToe xWinBeforeY = null;
        TicTacToe yWin = null;

        for(TicTacToe next : current.getAfterResults()) {
            if(yNotPossible == null && next.getWinningRankofY() == null) {
                yNotPossible = next;
            } else if(xWinBeforeY == null && next.getWinningRankofX() != null && next.getWinningRankofY() != null &&
                    next.getWinningRankofX() <
                    next
                    .getWinningRankofY()) {
                xWinBeforeY = next;
            } else if(next.getWinningRankofY() != null) {
                yWin = next;
            }
        }

        if(yNotPossible != null) {
            return yNotPossible;
        } else if(xWinBeforeY != null) {
            return xWinBeforeY;
        } else if(yWin != null) {
            return yWin;
        } else {
            int random = new Random().nextInt(current.getAfterResults().size()-1);
            return current.getAfterResults().get(random);
        }
    }

}
