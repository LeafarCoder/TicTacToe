import java.util.*;

public class Game {
	
	private int boardSize = 3;
	private int[][] gameState = new int[boardSize][boardSize];
	
	public Game(){
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				gameState[i][j] = 0;
			}
		}
	}
	
	public int checkWin(){
		
		for(int player = -1; player<=1; player+=2){
			boolean win = false;
			for(int i = 0; i < boardSize; i++){
				boolean win_row = true;
				boolean win_col = true;
				for(int j = 0; j < boardSize; j++){
					win_row &= (gameState[i][j] == player);
					win_col &= (gameState[j][i] == player);
				}
				win |= (win_row || win_col);
				if(win)return player;
			}
			
			boolean win_diag1 = true;
			boolean win_diag2 = true;
			for(int i = 0; i < boardSize; i++){
				win_diag1 &= (gameState[i][i] == player);
				win_diag2 &= (gameState[i][boardSize - i - 1] == player);			
			}
			win |= (win_diag1 || win_diag2);
			if(win)return player;
		}
		
		boolean full = true;
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				full &= gameState[i][j] != 0;
			}
		}
		if(full) return 2;
		
		return 0;
	}
	
	public void makeMove(int x, int y, int player){
		gameState[x][y] = player;
	}
	
	public boolean checkAvailability(int x, int y){
		return gameState[x][y] == 0;
	}
	
	public void printGame(){
		
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				System.out.print("|");
				if(gameState[i][j] == 1){
					System.out.print("X");
				}else if(gameState[i][j] == -1){
					System.out.print("O");
				}else{
					System.out.print(" ");
				}
				
				if(j == boardSize - 1){
					System.out.println("|");
				}
				
			}
			
			if(i != boardSize - 1)
				System.out.println("-------");
		}
	
	
	}
	
	public void copyBoard(Game g){
		for(int i = 0; i < boardSize; i++)
			for(int j = 0; j < boardSize; j++)
				this.gameState[i][j] = g.gameState[i][j];			
	}
	
	public int miniMax(int depth, Game gameBoard, int player){
		
		int checkWin = gameBoard.checkWin();
		if(depth == 0 || checkWin != 0){
			if(checkWin == 2)checkWin = 0;
			return checkWin;
		}
		
		if(player == 1){
			// player = 1
			int maxValue = -10;
			for(int i = 0; i < boardSize; i++){
				for(int j = 0; j < boardSize; j++){
					if(gameBoard.checkAvailability(i, j)){
						Game newGameBoard = new Game();
						newGameBoard.copyBoard(gameBoard);
						newGameBoard.makeMove(i, j, player);
						// newGameBoard.printGame();
						// System.out.println("\n\n");
						
						int v = miniMax(depth - 1, newGameBoard, -player);
						
						maxValue = Math.max(maxValue, v);
					}
				}
			}

			return maxValue;
			
		}else{
			// player = -1
			int minValue = 10;
			for(int i = 0; i < boardSize; i++){
				for(int j = 0; j < boardSize; j++){
					if(gameBoard.checkAvailability(i, j)){
						Game newGameBoard = new Game();
						newGameBoard.copyBoard(gameBoard);
						newGameBoard.makeMove(i, j, player);
						int v = miniMax(depth - 1, newGameBoard, -player);
						
						minValue = Math.min(minValue, v);
					}
				}
			}
			
			return minValue;
		}
	}

	public int[] getBestPosition(int depth, int player){
		List<int[]> ans = new ArrayList<int[]>();
		int maxValue = -10;

		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				Game auxGame = new Game();
				auxGame.copyBoard(this);

				if(auxGame.checkAvailability(i, j)){
					auxGame.makeMove(i, j, player);
					// auxGame.printGame();
					int v = miniMax(depth - 1, auxGame, -player);
					// System.out.println(v+"\n\n**************************************************\n\n ");
					if(maxValue < v){
						ans = new ArrayList<int[]>();
						maxValue = v;
					}
					if(maxValue == v){
						ans.add(new int[]{i,j});
					}
					
				}
			}
		}
		
		int idx = new Random().nextInt(ans.size());
		return ans.get(idx);
	}
}
