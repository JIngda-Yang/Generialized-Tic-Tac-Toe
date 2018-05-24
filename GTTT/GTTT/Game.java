package GTTT;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Game {

	private Board board;
    private String icharacter="We",ocharacter="They";
	public Game(int boardSize, int winningRowSize) {
		board = new Board(boardSize, winningRowSize);
	}

	public void AIvsAI() {
		Date date = new Date();
		System.out.println("Game start! AI 1 vs AI 2!");
		board.PrintBoard();
		while (true) {
			System.out.printf("******Round %d****%s**********\n", board.getCount(), date.toString());
			if (board.isOver()) {
				if (board.getWinner() == 2) {
					System.out.println("AI 2 win!");
				} else {
					System.out.println("This is a draw!");
				}
				break;
			}

			if (board.getCount() == 0) {
				board.move(board.getSize() / 2, board.getSize() / 2);

			} else {
				Alpha_Beta_Prunning.run(board.getPlayer(), board);
				System.out.println(board.lastX+" "+board.lastY);
			}

			board.PrintBoard();
			System.out.println(board.getScore1() + "," + board.getScore2());
			System.out.printf("******Round %d****%s**********\n", board.getCount(), date.toString());
			if (board.isOver()) {
				if (board.getWinner() == 1) {
					System.out.println("AI 1 win!");
				} else {
					System.out.println("This is a draw!");
				}
				break;
			}
			Alpha_Beta_Prunning.run(board.getPlayer(), board);
			System.out.println(board.lastX+" "+board.lastY);
			board.PrintBoard();
			System.out.println(board.getScore1() + "," + board.getScore2());
		}
	}


	public void PlayerMove() {
		int player = board.getPlayer();
		int x, y;
		String[] temp;

		// loop error check for the input, whether it is a solid move?
		while (true) {
			@SuppressWarnings("resource")
			Scanner keyboard = new Scanner(System.in);
			System.out.printf(
					"Player%d, please indicate your move by row,colomn, for example 5,7 means row 5 and column 7:\n",
					player);
			String input = keyboard.nextLine();
			temp = input.split(",");
			x = Integer.parseInt(temp[0]);
			y = Integer.parseInt(temp[1]);

			if (x < board.getSize() && y < board.getSize() && board.isEmpty(x, y))
				break;
			System.out.printf("Player%d, that isn't a valid move, please choose again.\n", player);
		}

		board.move(x, y);

	}

}
