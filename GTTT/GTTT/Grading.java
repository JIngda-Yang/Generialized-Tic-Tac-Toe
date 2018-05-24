package GTTT;

import java.util.Random;

public class Grading {
	

	private static Random rand = new Random();
	public static  double player1[] = new double[] {1.0,1.2};
	public static double player2[] = new double[] {1.2,1.0};
	private Grading(){}
	
    public static double getScore (Board board, int activePlayer) {

            if (activePlayer == 1)
            	return board.getScore1();  
            else
            	return board.getScore2();  

    }
    
    
    public static double evaluate(int[][] board, int x, int y, int winningRowSize,
    											int size, int hostPlayer){
    	
    	double incrementScore = 0;
    	int n = Math.min(x, winningRowSize-1);
    	int s = Math.min(size-winningRowSize-x, 0);
    	int w = Math.min(y, winningRowSize-1);
    	int e = Math.min(size-winningRowSize-y, 0);
    	
    	int nw = Math.min(n,w);
    	int se = Math.min(s,e);
    	
    	int ne = Math.min(n,Math.min(winningRowSize-1, size-1-y));
    	int sw = Math.min(s,Math.min(0, y-(winningRowSize-1)));
    	
    	int[] feature1 = new int[winningRowSize-1];
    	int[] feature2 = new int[winningRowSize-1];
    	int sum;
    	
    	for (int j = y-w; j<= y+e; j++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[x][j+k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[x][j+k] >=0){
   					sum += board[x][j+k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]++;
   			}else if (sum<0){
   				feature2[-sum-1]--;
   			}
    	}	
    	
    	for (int i = x-n; i<= x+s; i++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][y] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][y] >=0){
   					sum += board[i+k][y];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]++;
   			}else if (sum<0){
   				feature2[-sum-1]--;
   			}
    	}
    	
    	for (int i = x-nw, j = y-nw; i <= x+se;i++,j++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][j+k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][j+k] >=0){
   					sum += board[i+k][j+k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]++;
   			}else if (sum<0){
   				feature2[-sum-1]--;
   			}
    	}

    	for (int i=x-ne,j=y+ne;i<= x+sw;i++,j--){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][j-k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][j-k] >=0){
   					sum += board[i+k][j-k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]++;
   			}else if (sum<0){
   				feature2[-sum-1]--;
   			}
    	}
    	
    	board[x][y] = 0;
    	for (int j = y-w; j<= y+e; j++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[x][j+k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[x][j+k] >=0){
   					sum += board[x][j+k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]--;
   			}else if (sum<0){
   				feature2[-sum-1]++;
   			}
    	}	
    	
    	for (int i = x-n; i<= x+s; i++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][y] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][y] >=0){
   					sum += board[i+k][y];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]--;
   			}else if (sum<0){
   				feature2[-sum-1]++;
   			}
    	}
    	
    	//check \ diagonal
    	for (int i = x-nw, j = y-nw; i <= x+se;i++,j++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][j+k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][j+k] >=0){
   					sum += board[i+k][j+k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]--;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]++;
   			}
    	}

    	//check / diagonal
    	for (int i=x-ne,j=y+ne;i<= x+sw;i++,j--){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][j-k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][j-k] >=0){
   					sum += board[i+k][j-k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]--;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]++;
   			}
    	}
	
    	for (int i = 0;i<feature1.length;i++){
    		//System.out.printf("%d:%d, ", i+1 , feature[i]);
    		incrementScore += (i+1)*feature1[i]*Math.pow(2,i)*player1[hostPlayer-1]
    							+ (i+1)*feature2[i]*Math.pow(2,i)*player2[hostPlayer-1];
    	} 
    	
    	
    	return incrementScore*(0.9998+0.0002*rand.nextDouble());
    }

    

}
