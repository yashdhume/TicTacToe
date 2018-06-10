package TicTacToe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Spot {
    int x, y;
	public Spot(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
class Board{
	final static int size = 3;
	List<Spot> spotsAvailable;
	Scanner scan = new Scanner(System.in);
	int[][] board = new int[size][size];
	public Board(){
		//TODO something should go here idk
	}
	public boolean isGameOver(){
		return(XWon()||OWon()||getEmptySpots().isEmpty());
	}
    public void displayBoard() {
        System.out.println();
 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j]==1){
                    System.out.print("X ");
                }
                else if(board[i][j]==2){
                    System.out.print("O ");
                }
                else{
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }
	public boolean OWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
            // System.out.println("O");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                //  System.out.println("O");
                return true;
            }
        }
 
        return false;
    }
	public boolean XWon(){
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            //System.out.println("X");
            return true;
        }
        for (int i = 0; i < size; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                // System.out.println("X");
                return true;
            }
        }
        return false;
	}
	
	public List<Spot> getEmptySpots(){
		spotsAvailable = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(board[i][j]==0)
					spotsAvailable.add(new Spot(i,j));
			}
		}
		return spotsAvailable;
	}
	public void putMove(Spot Spot, int player){
		board[Spot.x][Spot.y]=player;//1=X 2=O
	}
    Spot computerMove;
    public int minimax(int depth, int turn) {
        if (XWon()) return +1;
        if (OWon()) return -1;
 
        List<Spot> pointsAvailable = getEmptySpots();
        if (pointsAvailable.isEmpty()) return 0;
 
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
 
        for (int i = 0; i < pointsAvailable.size(); i++) {
            Spot Spot = pointsAvailable.get(i);
            if (turn == 1) {
                putMove(Spot, 1);
                int currentScore = minimax(depth + 1, 2);
                max = Math.max(currentScore, max);
 
                if(currentScore >= 0){ 
                	if(depth == 0) 
                		computerMove = Spot;
                }
                if(currentScore == 1){
                	board[Spot.x][Spot.y] = 0; 
                	break;
                }
                if(i == pointsAvailable.size()-1 && max < 0){
                	if(depth == 0)
                		computerMove = Spot;
                	}
            	} 
            else if (turn == 2) {
                putMove(Spot, 2);
                int currentScore = minimax(depth + 1, 1);
                min = Math.min(currentScore, min);
                if(min == -1){
                	board[Spot.x][Spot.y] = 0; 
                	break;
                	}
            }
            board[Spot.x][Spot.y] = 0;
        }
        int finalReturn =0;
        if(turn ==1)
        	finalReturn=max;
        else
        	finalReturn = min;
        return finalReturn;
    }
}
public class TicTacToe {
	final static int size = 3;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Board brd = new Board();
		Random rnd = new Random();
		
		brd.displayBoard();
		int choice = rnd.nextInt(2);
		
		if(choice == 1){
			Spot Spot = new Spot(rnd.nextInt(size), rnd.nextInt(size));// plays it at a rnd box
			brd.putMove(Spot,1);
			brd.displayBoard();
		}
		int x=0, y=0;
		while (!brd.isGameOver()) {
            System.out.println("Your move: ");
           do{
	            System.out.print("Y cordinate: ");
	    		x = scan.nextInt();
	    		System.out.print("X cordinate: ");
	    		y = scan.nextInt();
           }while(x>2||y>2);
    		while (brd.board[x][y] == 1 || brd.board[x][y] == 2) {
                System.out.println("move wasnt correct try again");
                System.out.print("Y cordinate");
        		 x = scan.nextInt();
        		System.out.print("X cordinate");
        		 y = scan.nextInt();
            }
            Spot userMove = new Spot(x, y);
 
            brd.putMove(userMove, 2); 
            brd.displayBoard();
            if (brd.isGameOver()) break;
 
            brd.minimax(0, 1);
 
            brd.putMove(brd.computerMove, 1);
            brd.displayBoard();
        }
        if (brd.XWon())
            System.out.println("You lost!");
        else if (brd.OWon())
            System.out.println("You win!");
        else
        	System.out.println("Draw");
		scan.close();
	}
}
