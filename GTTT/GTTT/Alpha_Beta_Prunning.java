package GTTT;

public class Alpha_Beta_Prunning {
	private static int depth=3;
	private Alpha_Beta_Prunning(){}
	
	static void run(int activePlayer, Board board){
        AlphaBeta(activePlayer, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, activePlayer);
	}
	
	private static double AlphaBeta(int currentPlayer, Board board, double alpha, double beta, int currentDepth, int hostPlayer){
		if (currentDepth++ == depth || board.isOver()){
			return Grading.getScore(board,hostPlayer);
		}
		if (board.getPlayer()==1){
			return Max(1, board, alpha, beta, currentDepth, hostPlayer);
		}else{
			return Min(2, board, alpha, beta, currentDepth, hostPlayer);
		}
	}
 
    private static double Max (int player, Board board, double alpha, double beta, int currentDepth, int hostPlayer) {
        int BestMove = -1;

        for (Integer theMove : board.getAvailable()) {
            Board newBoard = board.Copy();
            newBoard.move(theMove/board.getSize(),theMove%board.getSize());
            double score = AlphaBeta(player, newBoard, alpha, beta, currentDepth,hostPlayer);
            if (score > alpha) {
                alpha = score;
                BestMove = theMove;          
            }
            if (alpha >= beta) {
                break;
            }
        }

        
        if (BestMove != -1) {
        	board.move(BestMove/board.getSize(),BestMove%board.getSize());
        }
        
        return alpha;
    }
    
    private static double Min (int player, Board board, double alpha, double beta, int currentDepth, int hostPlayer) {
        int BestMove = -1;

        for (Integer theMove : board.getAvailable()) {
            Board newBoard = board.Copy();
            newBoard.move(theMove/board.getSize(),theMove%board.getSize());
            double score = AlphaBeta(player, newBoard, alpha, beta, currentDepth,hostPlayer);
            if (score < beta) {
                beta = score;
                BestMove = theMove;  
            }
            if (alpha >= beta) {
                break;
            }
        }
        

        if (BestMove != -1) {
        	board.move(BestMove/board.getSize(),BestMove%board.getSize());
        }

        return beta;
    }
	
	
}
