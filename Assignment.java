import java.util.*;
import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.HashMap;

/**
* This is program that run simplify version of freecell
*
*@author ZhenXiong and Henry
**/

@SuppressWarnings("unchecked")
public class Assignment{
	
	public static void main(String[] args)throws IOException, InterruptedException{
		/**
		* @param input variable for Scanner
		* @param destinationColumn destination column
		* @param sourceColumn source column
		* @param temp to store the card that user choose
		* @param Deck hashmap to store all the card with mapped value
		* @param cloneDeck to copy the 
		*
		*@author ZhenXiong & Henry
		**/
		Scanner input = new Scanner(System.in);
		String destinationColumn, sourceColumn;
		String temp;	
		
		HashMap<Card,Integer> Deck = new HashMap<Card,Integer>();
		HashMap<Card,Integer> cloneDeck = new HashMap<Card,Integer>();
		
		Deck = createPiles();
		cloneDeck = (HashMap) Deck.clone();
		
		
		/**
		* @param columnList store all the column
		* @param spadeStack stack to store the spade cards
		* @param spadeStack heart to store the heart cards
		* @param spadeStack club to store the club cards
		* @param spadeStack diamond to store the diamond cards
		**/
		List<List<Card>> columnList=new ArrayList<List<Card>>();
		columnList=createColumnList(cloneDeck);
		
		PileStack spadeStack = new PileStack();
		PileStack heartStack = new PileStack();
		PileStack clubStack = new PileStack();
		PileStack diamondStack = new PileStack();
		
		
		
		
		//System.out.print(columnList.get(1).get(columnList.get(1).size()-1));
		do{
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			print(columnList,spadeStack,heartStack,clubStack,diamondStack);
			System.out.print("Command>> ");
			
			sourceColumn=input.next();
			
			if(sourceColumn.equals("r")){
				
				Deck = createPiles();
				cloneDeck = (HashMap) Deck.clone();
				columnList=createColumnList(cloneDeck);
				spadeStack.clear();
				heartStack.clear();
				clubStack.clear();
				diamondStack.clear();
			}
			else if(sourceColumn.equals("cr")){
				temp=input.next();
				if(isValidColumn(temp)){
					columnList.get(Integer.parseInt(temp)-1).add(0, columnList.get(Integer.parseInt(temp)-1).remove(columnList.get(Integer.parseInt(temp)-1).size()-1)); 
				}
				else{
					System.out.print("Invalid column\n");
					System.out.println("Press Any Key To Continue...");
					new Scanner(System.in).nextLine();
				}
			}
			else if(sourceColumn.equals("x")){
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				System.exit(0);
			}
			else{
				temp=input.next();
				destinationColumn = input.next();
				move(temp,sourceColumn,destinationColumn,columnList,spadeStack,heartStack,clubStack,diamondStack,Deck);
			}

		}while(!(spadeStack.size() == 13) || !(heartStack.size() == 13) || !(clubStack.size() == 13) || !(diamondStack.size() == 13));
		
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		print(columnList,spadeStack,heartStack,clubStack,diamondStack);
		System.out.print("Command>> \n");
		System.out.print("You solved the game!!!\n");
		System.out.println("Press Any Key To Continue...");
		new Scanner(System.in).nextLine();
		System.exit(0);
		
		
}
/**
* This is the method that move the card from column to column
*@param temp the card that user want to move
*@param sourceColumn source column
*@param destinationColumn destination column
*@param columnList list of column
*@param Deck all the card
*@param spadeStack spade stack
*@param heartStack heart stack
*@param clubStack club stack
*@param diamondStack diamond stack
**/
public static void move(String temp, String sourceColumn, String destinationColumn,List<List<Card>> columnList, PileStack spadeStack, PileStack heartStack, PileStack clubStack, PileStack diamondStack,HashMap Deck){
	
	if(isValidColumn(destinationColumn) && isValidColumn(sourceColumn)){
				if(!isPile(sourceColumn)){
					if(isPile(destinationColumn)){
						List<String> tempList= new ArrayList();
						
						for(int i=0 ;i<columnList.get(Integer.parseInt(sourceColumn)-1).size();i++){
							tempList.add(columnList.get(Integer.parseInt(sourceColumn)-1).get(i).toString());
						}
						
						if(tempList.contains(temp)){
							if(tempList.indexOf(temp) == (tempList.size()-1)){
								if(destinationColumn.equals("s")){
									if(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)).getPile().equals("s")){
										if(spadeStack.push(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)),Deck)){
											columnList.get(Integer.parseInt(sourceColumn)-1).remove(tempList.indexOf(temp));
										}
									}
								}
								else if(destinationColumn.equals("h")){
									if(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)).getPile().equals("h")){
										if(heartStack.push(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)),Deck)){
											columnList.get(Integer.parseInt(sourceColumn)-1).remove(tempList.indexOf(temp));
										}
									}
								}
								else if(destinationColumn.equals("c")){
									if(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)).getPile().equals("c")){
										if(clubStack.push(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)),Deck)){
											columnList.get(Integer.parseInt(sourceColumn)-1).remove(tempList.indexOf(temp));
										}
									}
								}
								else if(destinationColumn.equals("d")){
									if(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)).getPile().equals("d")){
										if(diamondStack.push(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)),Deck)){
											columnList.get(Integer.parseInt(sourceColumn)-1).remove(tempList.indexOf(temp));
										}
									}
								}
							}
							else{
								System.out.print("You cannot move the card that is not at the end of column\n");
								System.out.println("Press Any Key To Continue...");
								new Scanner(System.in).nextLine();
							}
						}
						else{
							System.out.print("There is no this card in the column\n");
							System.out.println("Press Any Key To Continue...");
							new Scanner(System.in).nextLine();
						}
					}
					else{
						List<String> tempList= new ArrayList();
						
						for(int i=0 ;i<columnList.get(Integer.parseInt(sourceColumn)-1).size();i++){
							tempList.add(columnList.get(Integer.parseInt(sourceColumn)-1).get(i).toString());
						}
						
						if(tempList.contains(temp)){
							if(tempList.indexOf(temp) == (tempList.size()-1)){
								int sourceValue = (int)Deck.get(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)));
								int destinationValue = (int)Deck.get(columnList.get(Integer.parseInt(destinationColumn)-1).get(columnList.get(Integer.parseInt(destinationColumn)-1).size()-1));
								if(sourceValue == destinationValue-1 ){
										
									columnList.get(Integer.parseInt(destinationColumn)-1).add(columnList.get(Integer.parseInt(sourceColumn)-1).get(tempList.indexOf(temp)));
									columnList.get(Integer.parseInt(sourceColumn)-1).remove(tempList.indexOf(temp));
								}
								else{
									System.out.print("Invalid move\n");
									System.out.println("Press Any Key To Continue...");
									new Scanner(System.in).nextLine();
								}
							}
							else{
								System.out.print("You cannot move the card that is not at the end of column\n");
								System.out.println("Press Any Key To Continue...");
								new Scanner(System.in).nextLine();
							}
						}
						else{
							System.out.print("There is no this card in the column\n");
							System.out.println("Press Any Key To Continue...");
							new Scanner(System.in).nextLine();
						}
					}						
				}
				else{
					System.out.print("You can't retrieve from piles!\n");
					System.out.println("Press Any Key To Continue...");
					new Scanner(System.in).nextLine();
				}
				
			}
			else{
				System.out.print("Invalid column!\n");
				System.out.println("Press Any Key To Continue...");
				new Scanner(System.in).nextLine();				
			}
}
/**
* This is the method that check is the input is stack or column
*@param temp is the card that user choose
*@return if it is a pile
**/
public static boolean isPile(String temp){
	if(temp.equals("s") || temp.equals("h") || temp.equals("c") || temp.equals("d"))
		return true;
	else 
		return false;
}

/**
* This is the method that check the validity of the column
*@param temp is the card that user choose
*@return if it is valid
**/	
public static boolean isValidColumn(String temp){
	
	if(Character.isDigit(temp.charAt(0))){
		int i = Integer.parseInt(temp);
		if(i>=1 && i<=9)
			return true;
		else 
			return false;
	}
	else{
		if(temp.equals("s") || temp.equals("h") || temp.equals("c") || temp.equals("d"))
			return true;
		else 
			return false;
	}
}
/**
* This is the method that print the columns and stacks
*@param columnList list of the columns
*@param s spade stack
*@param h heart stack
*@param c club stack
*@param d diamond stack
**/
public static void print(List<List<Card>> columnList,PileStack s,PileStack h,PileStack c,PileStack d){
	System.out.print("Pile s: "+s+"\n"+"Pile h: "+h+"\n"+"Pile c: "+c+"\n"+"Pile d: "+d+"\n"+
					"Column 1: "+columnList.get(0)+"\n"+"Column 2: "+columnList.get(1)+"\n"+
					"Column 3: "+columnList.get(2)+"\n"+"Column 4: "+columnList.get(3)+"\n"+"Column 5: "+columnList.get(4)+"\n"+
					"Column 6: "+columnList.get(5)+"\n"+"Column 7: "+columnList.get(6)+"\n"+"Column 8: "+columnList.get(7)+"\n"+
					"Column 9: "+columnList.get(8)+"\n"
					);
}
/**
* This is the method that create the card and pile 
*@return all the card created
**/
public static HashMap<Card,Integer> createPiles(){
	HashMap<Card,Integer> c = new HashMap<Card,Integer>();
	String pile;
	for(int i=0 ; i<4 ; i++){
		if(i==0)
			pile="s";
		else if(i==1)
			pile="h";
		else if(i==2)
			pile="c";
		else
			pile="d";
			
		for(int j=1 ; j<14 ; j++){
			if(j==1)
				c.put(new Card("A",pile),new Integer(j));
			else if(j==10)
				c.put(new Card("X",pile),new Integer(j));
			else if(j==11)
				c.put(new Card("J",pile),new Integer(j));
			else if(j==12)
				c.put(new Card("Q",pile),new Integer(j));
			else if(j==13)
				c.put(new Card("K",pile),new Integer(j));
			else
				c.put(new Card(String.valueOf(j),pile),new Integer(j));
		}
	}
	
	return c;
	
}
/**
* This is the method that store the card created into column list
*@param cloneDeck is the copy of the Deck 
*@return List of the column list 
**/
public static List<List<Card>> createColumnList(HashMap cloneDeck){
	List<List<Card>> columnList=new ArrayList<List<Card>>();
		
		List<Card> column1=new ArrayList<Card>();
		List<Card> column2=new ArrayList<Card>();
		List<Card> column3=new ArrayList<Card>();
		List<Card> column4=new ArrayList<Card>();
		List<Card> column5=new ArrayList<Card>();
		List<Card> column6=new ArrayList<Card>();
		List<Card> column7=new ArrayList<Card>();
		List<Card> column8=new ArrayList<Card>();
		List<Card> column9=new ArrayList<Card>();
		
		
		columnList.add(column1);
		columnList.add(column2);
		columnList.add(column3);
		columnList.add(column4);
		columnList.add(column5);
		columnList.add(column6);
		columnList.add(column7);
		columnList.add(column8);
		columnList.add(column9);
		
		for(int i=0; i<7; i++){
			for(int j=0; j<8; j++){
				if(!cloneDeck.isEmpty()){
					Object c= cloneDeck.keySet().toArray()[new Random().nextInt(cloneDeck.keySet().toArray().length)];
					columnList.get(j).add((Card)c);	
						for(Iterator<Card> iterator = cloneDeck.keySet().iterator(); iterator.hasNext();){
							Card temp = iterator.next();
							
							if(temp.equals(c)){
								iterator.remove();
							}
						}
				}
			}
		}
		
		return columnList;
}
}
/**
* This is the self define stack by using arraylist
*@param top is to get the last value of the stack
*@param a is arraylist to store the value
**/
class PileStack { 
    
    int top; 
    ArrayList<Card> a;  
	

/**
* This is to check if the stack is empty or not
*@return is the stack empty or not
**/
    boolean isEmpty() 
    { 
        return (top < 0); 
    } 
/**
* This is the constructor of the class
**/	
    PileStack() 
    { 
		a = new ArrayList<Card>();
        top = -1; 
    } 
/**
* This is to check if the stack is empty or not
*@param value is to get the value of the card to use for comparing
*@return if any card has pushed into the stack
**/  
    boolean push(Card x,HashMap Deck) 
    { 
 
		int value = (int)Deck.get(x);
			if(a.isEmpty()){
				if(value == 1){
					a.add(x); 
					System.out.println(x + " pushed into stack\n"); 
					System.out.println("Press Any Key To Continue...");
					new Scanner(System.in).nextLine();
					return true;
				}
				else{
					System.out.print("Invalid move\n");
					System.out.println("Press Any Key To Continue...");
					new Scanner(System.in).nextLine();
					return false;
				}
			}
			else{
				if(value == this.peek(Deck)+1){
					a.add(x); 
					System.out.println(x + " pushed into stack\n"); 
					System.out.println("Press Any Key To Continue...");
					new Scanner(System.in).nextLine();
					return true;
				}
				else{
					System.out.print("Invalid move\n");
					System.out.println("Press Any Key To Continue...");
					new Scanner(System.in).nextLine();
					return false;
				}
				}
			}  
  
 /**
* To get the last element of the stack without deleting iterator
*@return the value of the last element
**/
    int peek(HashMap Deck) 
    { 
        if (a.isEmpty()) { 
            System.out.println("Stack Underflow"); 
            return 0; 
        } 
        else { 
            return (int)Deck.get(a.get(a.size()-1)); 
        } 
    }
/**
* This is to empty the stack
**/	
	void clear(){
		a.clear();
	}
/**
* This is to get the size of the stack
*@return the size of the stack
**/	
	int size(){
		return a.size();
	}

	public String toString(){
		 return a.toString();
	}
}

/**
* This is the card class
*@param face the face of the card
*@param pile which pile does the card belong to
**/
class Card{
	String face;
	String pile;
/**
* This is the constructor for the card
**/	
	public Card(String f, String p){
		face=f;
		pile=p;
	}
/**
* This is get the face of the card
*@return face of the card
**/	
	public String getFace(){
		return face;
	}
/**
* This is get the pile of the card
*@return pile of the card
**/		
	public String getPile(){
		return pile;
	}
/**
* This is the toString method
*@return face and card in string
**/		
	public String toString(){
		return pile+face;
	}
} 
