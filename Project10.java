/*
 * Project10.java
 * 
 *   A program that plays and scores a round of the game Poker Dice. 
 *   
 *   EXTRA CREDIT COMPLETED: implemented:
 *   	* Straight score condition
 *      
 * @author William Matera
 * @version 11112015
 * 
 */
import java.util.Arrays;
import java.util.Scanner;

public class Project10 {

	public static void main(String[] args) 
	{//Start of main 
  Scanner in = new Scanner(System.in);
	int diceCT = 5;
	int [] dice;
		dice = new int[diceCT];//Declare and allocate memory for 5 dice array
	boolean replay = true;
		while (replay)//Start game
		{
			resetDice(dice);
			rollDice(dice);//Roll all 5 dice
		
		String output = diceToString(dice);
			System.out.println("Your current dice: " +output);
			promptForReroll(dice, in);//Ask user which dice to Re-roll			
	
	System.out.println("Keeping remaining dice...");
	System.out.println("Rerolling...");
			rollDice(dice);
			
			output = diceToString(dice);
	System.out.println("Your final dice: " + output);
	 		
			String result = getResult(dice);//Find highest score
	System.out.println(result);
	
	replay = promptForPlayAgain(in);//Ask user to play again
		
		}
	System.out.print("GoodBye!");
	}//End of main
	
	
	private static void resetDice(int[] dice) 
	{//Start all dice with value of 0
		for (int i = 0; i < dice.length; i++)
		{dice[i] = 0;}
	}
	
	private static void rollDice(int[] dice) 
	{//For each dice with value of zero, generate random roll of numbers between 1 - 6
		for (int i = 0; i < dice.length; i++)
		{
			if (dice[i] == 0)//Check which dice need to be rolled
			{dice[i] = (int)( Math.random()* 6) + 1;}
		}
	}
	
	private static String diceToString(int[] dice) 
	{//Library method Arrays.toString() from java documentation
		String output = Arrays.toString(dice);
		return output;
	}
	
	private static void promptForReroll(int[] dice, Scanner in) 
	{//After initial roll, ask user which dice to Re-roll before generating score of all 5 dice
		int timer = 0;
			while (timer != 1)//User can choose to Re-roll multiple dice
			{
			  System.out.print("Select a die to re-roll (-1 to keep remaining dice): ");
			  int pick = in.nextInt();
				if (pick == -1)//check if user chooses to keep remaining dice
				{break;}
				 
				    while (pick < 0 || pick > dice.length-1)//Validate input
					{
						System.out.println("Error: Index must be between 0 and 4 (-1 to quit)!");
						System.out.print("Select a die to re-roll (-1 to keep remaining dice): ");
						pick  = in.nextInt();
					}
				dice[pick] = 0;
			String update = Arrays.toString(dice);
			System.out.println("Your current dice: " +update);
			}
	}
	
	private static boolean promptForPlayAgain(Scanner in) 
	{//Ask user to play another new round
		boolean replay = true;
		String clearLine = in.nextLine();//clear scanner
		System.out.print("Would you like to play again [Y/N]?: ");
			String input = in.nextLine();
			System.out.print('\n');
				char ask = input.charAt(0);
				char askUP = Character.toUpperCase(ask);//handle lower case and upper case input
			
			while (askUP != 'Y' && ask != 'N')//Validate input
			{
				 System.out.println("ERROR! Only 'Y' or 'N' allowed as input!");
				 System.out.print("Would you like to play again [Y/N]?: ");
				 input = in.nextLine();
				 ask = input.charAt(0);
				 askUP = Character.toUpperCase(ask);
				 System.out.print('\n');
			}
			
			if (askUP == 'Y')
			{replay = true;}
			else if (askUP == 'N')
			{replay = false;}
			
		return replay;
	}
	
	private static String getResult(int[] dice) 
	{//Get score of user's final 5 dice
		int [] temp;
		temp = new int[6];//declare & allocate new array to count occurrences of numbers 1 - 6
		temp = getCounts(dice);//Get counts of each possible number
		boolean fullHouse = false;
		boolean twoPair = false;
		int max = temp[0];
		int indexMax = 0;
			for (int z = 1; z < temp.length; z++)
			{//Look for the highest occurrence out of all values 
				if (temp[z] > max)
				{max = temp[z];
				 indexMax = z;}		
			}
				
				if (max == 3)
				{//check if user has a Full House
					for (int i = 0; i < temp.length; i++)
					{
						if (temp[i] == 2)
						{fullHouse = true;}					
					}
				}
				else if (max == 2)
				{//check if user has two separate pairs
					for (int i = 0; i < temp.length; i++)
					{
						if (temp[i] == 2 && i != indexMax)
						{twoPair = true;}					
					}
				}
			
				boolean straight = straight(dice);
			String userResult = "";
				
			if (straight)
			{userResult = "Straight!";}
			else
			{userResult = wins(dice, max, fullHouse, twoPair);}//Retrieve user's highest score
	
	return userResult;	
	}
	
	private static boolean straight(int[] dice)
	{//EXTRA CREDIT: Program can handle, decipher and output the straight hand score
		boolean yes = false;
		int [] hold;
		hold = new int[dice.length];
		hold = dice;
		Arrays.sort(hold);//sort array 
			int i = 0;
			int j = 0;
			for (j = 1; j < hold.length; j++)
			{
			  	if (hold[j] == (hold[i]+1))//check for 1 2 3 4 5 OR 2 3 4 5 6 
			  	{
			  	 yes = true;
			  	}
			  	else
			  	{
			  	 yes = false;
			  	 break;
			    } 
			i++;
			}
	return yes;
	}
	
	private static String wins(int[] dice, int max, boolean fullHouse, boolean twoPair)
	{//create message to print-out to user of highest score 
		String test = null;
			if (fullHouse)
				{test = "Full House!";}
			else if (twoPair)
				{test = "Two Pair!";}
			else if (max > 1)
				{ 
					switch (max){
						case 5: 
							test = "Five of a kind!";
							break;
						case 4: 
							test = "Four of a kind!";
							break;
						case 3: 
							test = "Three of a kind!";
							break;
						case 2: 
							test = "One Pair!";
							break;					
								}
				}
			else//If no pairs or Straight, return the value of highest dice
				{
				int maxValue = dice[0];
					for (int i = 1; i < dice.length; i++)
					{
						if (dice[i] > max)
						{maxValue = dice[i];}		
					}
				test = "Highest card " + maxValue;
				}
		return test;
	}
	
	private static int[] getCounts(int[] dice) 
	{//Count occurrences of each possible number of all 5 dice
		
		int [] counts;
		counts = new int[6];//Declare and allocate new array to hold number of occurrences per possibility
			int placeVal = 1;
			int i = 0;
			for (i = 0; i < counts.length; i++)
			{
			  int ct = 0;
			  int z = 0;
				while (z < 5) 
				{
					if (dice[z] == placeVal)
					{ct++;}
				z++;
				}
			 counts[i] = ct;
			 placeVal++;
			}
	   return counts;
	}
}