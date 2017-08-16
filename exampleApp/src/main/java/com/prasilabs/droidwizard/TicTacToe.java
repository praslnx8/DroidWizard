package com.prasilabs.droidwizard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Contus team on 11/7/17.
 */

public class TicTacToe {
    private XY[][] board = new XY[3][3];
    private XY winner;
    private XY player = XY.Y;
    private Integer winningRankofX = null;
    private Integer winningRankofY = null;
    private TicTacToe parent;

    public XY[][] getBoard() {
        return board;
    }

    public Integer getWinningRankofX() {
        return winningRankofX;
    }

    public Integer getWinningRankofY() {
        return winningRankofY;
    }

    public List<TicTacToe> getAfterResults() {
        return afterResults;
    }

    public void setAfterResults(List<TicTacToe> afterResults) {
        this.afterResults = afterResults;
    }

    private List<TicTacToe> afterResults = new ArrayList<>();

    public TicTacToe(TicTacToe parent) {
        this.parent = parent;
    }

    public void computeTicTacToe() {

        System.out.println("Playing  : " + this.player);

        winner = getWinner();
        if(winner != null) {
            System.out.println("winner is : " + winner.toString());
            System.out.println();
            if(winner == XY.X) {
                winningRankofX = 0;
            } else {
                winningRankofY = 0;
            }
            setParentRank();
        } else {
            for(int i=0; i<board.length; i++) {
                for(int j=0; j<board.length; j++) {
                    if(board[i][j] == null) {
                        TicTacToe ticTacToe = new TicTacToe(this);
                        copyTicTacToe(ticTacToe);
                        XY newPlayer = player.switchPlayer();
                        ticTacToe.board[i][j] = newPlayer;
                        ticTacToe.player = newPlayer;
                        afterResults.add(ticTacToe);
                    }
                }
            }

            for(TicTacToe ticTacToe : afterResults) {
                ticTacToe.computeTicTacToe();
            }
        }
    }

    public void copyTicTacToe(TicTacToe newTicTacToe) {
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board.length; j++) {
                newTicTacToe.board[i][j] = board[i][j];
            }
        }
    }

    public XY getWinner() {
        if((board[0][0] == board[1][1]) && (board[1][1] == board[2][2])) {
            return board[0][0];
        } else if((board[0][2] == board[1][1]) && (board[1][1] == board[2][0])) {
            return board[0][2];
        } else if((board[0][0] == board[0][1]) && (board[0][1] == board[0][2])) {
            return board[0][0];
        } else if((board[1][2] == board[1][1]) && (board[1][1] == board[1][0])) {
            return board[1][2];
        } else if((board[2][0] == board[2][1]) && (board[2][1] == board[2][2])) {
            return board[2][0];
        } else if((board[0][0] == board[1][0]) && (board[1][0] == board[2][0])) {
            return board[0][0];
        } else if((board[0][1] == board[1][1]) && (board[1][1] == board[2][1])) {
            return board[0][1];
        } else if((board[0][2] == board[1][2]) && (board[1][2] == board[2][2])) {
            return board[0][2];
        }

        return null;
    }

    void setParentRank() {
        if(parent != null) {
            if(winningRankofX != null) {
                if(parent.winningRankofX == null || parent.winningRankofX > winningRankofX) {
                    parent.winningRankofX = winningRankofX+1;
                    parent.setParentRank();
                }
            }
            if(winningRankofY != null) {
                if(parent.winningRankofY == null || parent.winningRankofY > winningRankofY) {
                    parent.winningRankofY = winningRankofY+1;
                    parent.setParentRank();
                }
            }
        }
    }

    public boolean isSame(XY[][] otherBoard) {
        for(int i=0;i<board.length; i++) {
            for(int j=0; j<board.length; j++) {
                if(board[i][j] != otherBoard[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public void print() {
        for(int i=0; i<board.length; i++) {
            if(i == 0) {
                System.out.print("------");
                System.out.println();
            }
            for(int j=0; j<board.length; j++) {
                if(j == 0) {
                    System.out.print("|");
                }
                if(board[i][j] == null) {
                    System.out.print(" ");
                } else if(board[i][j] == XY.X) {
                    System.out.print("X");
                } else if(board[i][j] == XY.Y) {
                    System.out.print("Y");
                }
                if(j == board.length-1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if(i == board.length -1) {
                System.out.print("------");
                System.out.println();
            }
        }
    }
}
