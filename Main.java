import java.util.Scanner;

public class Main {
	
	public static void main(String[] args){
		
		Game game = new Game();
		System.out.println(game);
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		
		int player = -1;
		while(game.checkWin() == 0){
			player *= -1;
			//System.out.println(player);
			if(player == 1){
				int[] move = game.getBestPosition(10, player);
				//System.out.println("MOVE: "+move[0]+","+move[1]);
				game.makeMove(move[0], move[1], player);
				
				game.printGame();
				System.out.println("\n");
			}else{
				System.out.print("\nNew move: ");
				int x = reader.nextInt();
				int y = reader.nextInt();

				game.makeMove(x, y, player);
			}
			
			
			
		}
		
		int checkWin = game.checkWin();
		
		switch (checkWin) {
		case 2:
			System.out.println("Empate!");
			break;
		case 1:
			System.out.println("'X' ganha!");
			break;
		case -1:
			System.out.println("'O' ganha!");
		}
		
	}
}