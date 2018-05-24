package GTTT;

import java.util.HashSet;

public class Board {

	private int size = 3;
	private int Link_size = 3;

	private int[][] board;
	private int player;
	private int winner;
	private HashSet<Integer> Available;

	private double score1 = 0;
	private double score2 = 0;
	private int Count;
	private boolean Over;
	public int lastX, lastY;
	public static final char[] token = { '.', 'X', 'O' };

	public Board(int size, int winningRowSize) {
		this.size = size;
		this.Link_size = winningRowSize;
		this.board = new int[size][size];
		Available = new HashSet<>();
		init();
	}

	void init() {
		Count = 0;
		Over = false;
		player = 1;
		winner = 0;
		score1 = 0;
		score2 = 0;
		initialize();
	}

	private void initialize() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = 0;
			}
		}
		Available.clear();
	}

	private int hash(int x, int y) {
		return x * size + y;
	}

	public boolean move(int x, int y) {
		if (Over) {
			throw new IllegalStateException("Generalized TicTacToe is over. No moves can be played.");
		}
		if (board[x][y] == 0) {
			board[x][y] = player;
			lastX = x;
			lastY = y;
			Count++;
			Over = (size * size == Count);
			winner = isWin(x, y);
			if (winner != 0) {
				Over = true;
			}

			if (Over && winner == 1) {
				score1 = score2 = Integer.MAX_VALUE - Count;
			} else if (Over && winner == 2) {
				score1 = score2 = Integer.MIN_VALUE + Count;
			} else {
				int[][] parsedBoard = new int[size][size];
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (board[i][j] == 2) {
							parsedBoard[i][j] = -1;
						} else {
							parsedBoard[i][j] = board[i][j];
						}
					}
				}
				score1 += Grading.evaluate(parsedBoard, x, y, Link_size, size, 1);
				parsedBoard[x][y] = (player == 1 ? 1 : -1);
				score2 += Grading.evaluate(parsedBoard, x, y, Link_size, size, 2);
			}
		} else {
			return false;
		}
		// refresh the avaiable moves
		int x_start, x_end, y_start, y_end;
		x_start = Math.max(x - Link_size + 1, 0);
		x_end = Math.min(x + Link_size - 1, size - 1);
		y_start = Math.max(y - Link_size + 1, 0);
		y_end = Math.min(y + Link_size - 1, size - 1);
		for (int i = x_start; i <= x_end; i++) {
			if (board[i][y] == 0)
				Available.add(hash(i, y));
		}
		for (int j = y_start; j <= y_end; j++) {
			if (board[x][j] == 0)
				Available.add(hash(x, j));
		}
		for (int i = x, j = y; i >= x_start && j >= y_start; i--, j--) {
			if (board[i][j] == 0)
				Available.add(hash(i, j));
		}
		for (int i = x, j = y; i <= x_end && j <= y_end; i++, j++) {
			if (board[i][j] == 0)
				Available.add(hash(i, j));
		}

		for (int i = x, j = y; i >= x_start && j <= y_end; i--, j++) {
			if (board[i][j] == 0)
				Available.add(hash(i, j));
		}
		for (int i = x, j = y; i <= x_end && j >= y_start; i++, j--) {
			if (board[i][j] == 0)
				Available.add(hash(i, j));
		}
		Available.remove(hash(x, y));

		// change player
		player = 3 - player;
		return true;
	}

	// Check whether the game reach a draw or win.
	public boolean isOver() {
		return Over;
	}

	// Check who win the game
	public int getWinner() {
		if (!Over) {
			throw new IllegalStateException("The game is not over yet.");
		}
		return winner;
	}

	// Check whose turn it is.
	public int getPlayer() {
		return player;
	}

	public double getScore1() {
		return score1;
	}

	public double getScore2() {
		return score2;
	}

	public int getLink_size() {
		return Link_size;
	}

	public int getSize() {
		return size;
	}

	public int getCount() {
		return Count;
	}

	public boolean isEmpty(int x, int y) {
		return board[x][y] == 0;
	}

	public Board Copy() {
		Board board = new Board(this.size, this.Link_size);
		for (int i = 0; i < board.board.length; i++) {
			board.board[i] = this.board[i].clone();
		}
		board.player = this.player;
		board.winner = this.winner;
		board.Available = new HashSet<>();
		board.Available.addAll(this.Available);
		board.Count = this.Count;
		board.Over = this.Over;
		board.score1 = this.score1;
		board.score2 = this.score2;
		return board;
	}

	public HashSet<Integer> getAvailable() {
		return Available;
	}

	// to array of value 1 and -1.
	int[][] toArray() {
		int[][] result = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == 2) {
					result[i][j] = -1;
				} else {
					result[i][j] = board[i][j];
				}
			}
		}
		return result;
	}

	// Check gameover of current board based on the latest move.
	public int isWin(int x, int y) {
		int count;
		int i;
		// check all possible row win
		count = 1;
		i = 1;
		while (count < Link_size && y - i >= 0 && board[x][y - i] == player) {
			count++;
			i++;
		}
		if (count == Link_size) {
			return player;
		}

		i = 1;
		while (count < Link_size && y + i <= size - 1 && board[x][y + i] == player) {
			count++;
			i++;
		}
		if (count == Link_size) {
			return player;
		}

		// check all possible column win
		count = 1;
		i = 1;
		while (count < Link_size && x - i >= 0 && board[x - i][y] == player) {
			count++;
			i++;
		}
		if (count == Link_size) {
			return player;
		}

		i = 1;
		while (count < Link_size && x + i <= size - 1 && board[x + i][y] == player) {
			count++;
			i++;
		}
		if (count == Link_size) {
			return player;
		}
		// check all possible \ win
		count = 1;
		i = 1;
		while (count < Link_size && x - i >= 0 && y - i >= 0 && board[x - i][y - i] == player) {
			count++;
			i++;
		}
		if (count == Link_size) {
			return player;
		}

		i = 1;
		while (count < Link_size && x + i <= size - 1 && y + i <= size - 1 && board[x + i][y + i] == player) {
			count++;
			i++;
		}
		if (count == Link_size) {
			return player;
		}
		// check all possible / win
		count = 1;
		i = 1;
		while (count < Link_size && x + i <= size - 1 && y - i >= 0 && board[x + i][y - i] == player) {
			count++;
			i++;
		}
		if (count == Link_size) {
			return player;
		}

		i = 1;
		while (count < Link_size && x - i >= 0 && y + i <= size - 1 && board[x - i][y + i] == player) {
			count++;
			i++;
		}
		if (count == Link_size) {
			return player;
		}

		return 0;
	}

	public void PrintBoard() {
		/* Print first line */
		System.out.print("x\\y|");
		for (int i = 0; i < size; i++) {
			System.out.printf("%2d", i);
		}
		System.out.print("\n");

		/* Print second line */
		System.out.print("---|");
		for (int i = 0; i < size; i++) {
			System.out.print("--");
		}
		System.out.print("\n");

		/* Print the rest */

		for (int i = 0; i < size; i++) {
			System.out.printf("%2d |", i);
			for (int j = 0; j < size; j++) {
				System.out.print(" " + token[board[i][j]]);
			}
			System.out.print("\n");
		}
	}

}
