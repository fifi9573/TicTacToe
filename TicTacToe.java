import java.util.Scanner;

public class TicTacToe  {

	/*
	 * Declare instance variables
	 */
	 
	private char[][] board; //the game board is a 2D array
	private boolean xTurn; //true if X's turn, false if O's turn
	private Scanner keyboard; //Scanner for reading things in from keyboard
	                          //declares variable to be from my scanner class so that i can call on it (found out i could do this online)           
	
	
	/*
	 * Constructor, sets things up for the game to run
	 */
	
	public TicTacToe()  {
	
		//create the board
		board = new char[3][3];//makes my board a 2D array so that it would create a 3 by 3 grid
			
		//initialize the board to all spaces
		for(int r = 0; r < 3; r++)  {
			
			for(int c = 0; c < 3; c++)
				board[r][c] = ' ';//makes it so that it prints a board with every space
		}
		
		//it's X's turn when we start
		xTurn = true;
		
		//create our keyboard object
		keyboard = new Scanner(System.in);
	}
	
	/*
	 * Displays a single row of the game board specified by the row parameter
	 */
	
	private void displayRow(int row)  {
	
		System.out.println(" " + board[row][0] + " | " + board[row][1] + " | " + board[row][2]);//makes a line in between every space so that the board is sparates into sections in each column   
	}
	
	/*
	 * Displays the entire game board
	 */
	
	private void displayBoard()  {
	
		displayRow(0);//prints each row individually
		System.out.println("-----------");//separates each row with the dotted line
		displayRow(1);
		System.out.println("-----------");
		displayRow(2);
	}
	
	/*
	 * Displays the basic menu options available to the user
	 */
	
	private void displayMenu()  {
	
		//print whose turn it is
		if(xTurn)
			System.out.println("X's Turn!");//will clearly tell the player who's turn it is so the player does not get confused
		else
			System.out.println("O's Turn!");
			
		//print the options menu			
		System.out.println("What would you like to do?");//gives them a choice as to what they want to do
		System.out.println("1: Make a move");
		System.out.println("2: Start Over");
		System.out.println("3: Quit");
		System.out.print("Choice: ");//they will choose by typing the number of their choice
	}
	
	/*
	 * Gets the position from the user of where the next move should be made
	 * The board is then updated with a valid move
	 *
	 * Returns true if there is a winner and false if there is no winner
	 * 
	 */
	
	private boolean getMove()  {
	
		boolean invalid = true;
		int row = 0, column = 0;
		
		//keep asking for a position until the user enters a valid one
		while(invalid)  {
	
			System.out.println("Which row and column would you like to move to? Enter TWO numbers between 0-2 separated by a space to indicate the position.");
			row = keyboard.nextInt(); //sets the row equal to the first number entered and the column equal to the second
			column = keyboard.nextInt();
			
			//check that the position is within bounds
			if(row >= 0 && row <= 2 && column >= 0 && column <= 2)  {//provided that the position exists
			
				//check that the position is not already occupied
				if(board[row][column] != ' ')//must qual a space for it to print
					System.out.println("That position is already taken");
				else
					invalid = false;
			}
			else
				System.out.println("Invalid position");
		}
		
		//fill in the game board with the valid position
		if(xTurn)
			board[row][column] = 'X';
		else
			board[row][column] = 'O';
		
		return winner(row,column);
	}
	
	/*
	 * Starts the game over by resetting variables
	 */
	
	private void restart()  {
	
		//empty the game board
		for(int r = 0; r < 3; r++)  {
			
			for(int c = 0; c < 3; c++)
				board[r][c] = ' ';
		}

			
		//reset whose turn it is
		xTurn = true;//X is always going to be first
	}
	
	/*
	 * Given the row and column where the last move was made, this method
	 * return true if the move resulted in a win and false otherwise
	 */
	
	private boolean winner(int lastR, int lastC)  {
	
		boolean winner = false; //assume there's no winner
		char symbol = board[lastR][lastC]; //the symbol for the last move either X or O
		
		//check left-right
		//the last move we made was in the row lastR, check that row for three of the same symbol
		int numFound = 0;
		for(int c = 0; c < 3; c++)  {
			if(board[lastR][c] == symbol)
				numFound++;
		}
		
		if(numFound == 3)
			winner = true;
	
		//check up-down
		//the last move we made was in the column lastC, check that column for three of the same symbol
		numFound = 0;
		for(int r = 0; r < 3; r++)  {
			if(board[r][lastC] == symbol)
				numFound++;
		}
		
		if(numFound == 3)
			winner = true;

		//check both diagonals
		numFound = 0;
		for(int i = 0; i < 3; i++)  {
			if(board[i][i] == symbol)
				numFound++;
		}
		
		if(numFound == 3)
			winner = true;
		
		numFound = 0;
		for(int i = 0; i < 3; i++)  {
			if(board[i][2-i] == symbol)
				numFound++;
		}

		if(numFound == 3)
			winner = true;
			
		return winner;
	}
	
	/*
	 * Checks whether the board is full and the game is over
	 *
	 * Return true if full and false otherwise
	 */
	
	private boolean boardFull()  {
	
		//find the number of spots that are occupied by either an X or an O
		int numSpotsFilled = 0;
		
		for(int r = 0; r < 3; r++)  {
			
			for(int c = 0; c < 3; c++)  {
				if(board[r][c] == 'X' || board[r][c] == 'O')
					numSpotsFilled++;
			}
		}
		
		return numSpotsFilled == 9;
	}
	
	/*
	 * Main entry point to the game.  Starts the game.
	 */
	
	public void play()  {
	
		while(true)  {
		
			displayBoard();
			displayMenu();
			
			int choice = keyboard.nextInt();
		
			if(choice == 1)  {
			
				if(getMove())  {
					//we have a winner!
					displayBoard();	//display board one last time
					
					if(xTurn)
						System.out.println("X Wins!");
					else
						System.out.println("O Wins!");
						
					System.exit(0);
				}
				else if(boardFull())  {
					//we have a draw
					displayBoard(); //display board one last time
					
					System.out.println("Draw!");
					
					System.exit(0);
				}
				else  {
					//no winner yet
					xTurn = !xTurn;  //switch whose turn it is
				}
			}
			else if(choice == 2)
				restart();
			else if(choice == 3)
				System.exit(0);	//quit
			else
				System.out.println("Invalid Option");
		}	
	}
}
