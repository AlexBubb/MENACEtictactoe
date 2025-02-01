//Alex Bubb Final Coding Project Period 6
//READ THE README theres some cool stuff in there
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Main {
  public static void main(String[] args) {
    System.out.println("When the computer is ready type the corresponding number to place your move on the board.");
    System.out.println("WARNING: do not try to place a move on top of another letter.");
    System.out.println("compiled!");
    //creates each box (think map of game)
    ArrayList<box> boxes = new ArrayList<box>();
    System.out.println("Searching...");
    boxes = search();
    //teaches each box how to act when opened
    System.out.println("Learning...");
    boxes = learn(boxes,new ArrayList<Integer>());
    //play the game of computer vs human (spoiler: unwinable for human)
    while (true){
    play(boxes);}
  }
  //takes in the computer and uses it to play tic tac toe
  public static void play (ArrayList<box> boxes) {
    //setup starting game
    Scanner scan = new Scanner(System.in);
    int playermove;
    int id=0;
    int [] [] board = {{0,0,0},{0,0,0},{0,0,0}};
    //game cannot last more than  moves based on rules of tic tac toe
    for (int i = 0; i<5;i++) {
      showboard(board);
      //player moves
      System.out.println("Enter your move: ");
      playermove = scan.nextInt()-1;
      board[playermove/3][playermove%3] = 1;
      //checks if won (will never be used)
      if(win(board)) {
        showboard(board);
        System.out.println("Player one wins!");
        System.out.println("hit enter to play again");
        scan.nextLine();
        scan.nextLine();
        return;
      }
      //finds the computers response
      id = findid(board);
      if (boxes.get(idat(boxes,id)).getmoves().size()==0)
        break;
      board = decodeid(boxes.get(idat(boxes,id)).makemove());
      if(win(board)) {
        showboard(board);
        System.out.println("Player two wins!");
        System.out.println("hit enter to play again");
        scan.nextLine();
        scan.nextLine();
        return;
      }
    }
    //if the game has reached stalemate the loop will finish
    showboard(decodeid(id));
    System.out.println("Tie :/");
    System.out.println("hit enter to play again");
    scan.nextLine();
    scan.nextLine();
  }
  //learn teaches the boxes what moves they should make
  public static ArrayList<box> learn (ArrayList<box> boxes,ArrayList<Integer> banned) {
    ArrayList<Integer> temp = new ArrayList<Integer>();
    int [] [] board = new int [3] [3];
    int [] [] temp2 = new int [3] [3];
    nextb:
    for (box b: boxes) {
      if(!banned.contains(b.getid())) {
        int loser = 0;
        nextm:
        for(int u = 0; u<b.getmoves().size(); u++) {
          board = decodeid(b.getmoves().get(u));
          //if a box can win the game set it sutch that its the only avalible move
          if (win(board)) {
            b.setwin(true);
            temp = new ArrayList<Integer>();
            temp.add(b.getmoves().get(u));
            b.setmove(new ArrayList<Integer>(temp));
            continue nextb;
          }
          //otherwise for each move find if human can win and if they can remove that move leaving only the blocking move avalible
          for(int g = 0; g<3;g++) {
            for(int h = 0; h<3;h++) {
              if (board[g][h] == 0) {
                temp2 = new int [3] [3];
                for (int j = 0;j<3;j++) {temp2[j]=board[j].clone();}
                temp2[g][h] = 1;
                if (win(temp2)||banned.contains(findid(temp2))) {
                  loser++;                  
                  b.removemove(u);
                  u--;
                  //if there is a senario where human has created a fork treat this box as a lossing box and if human can reach that position never let it reach it (needs to rerun to check priviouse boxes for ways to reach unwinable position)
                  if (b.getmoves().size()==0) {
                    banned.add(b.getid());
                    return learn(boxes,banned);
                  }
                  continue nextm;
                }
              }
            }
          }
        }
      }
    }
    System.out.println(banned.size());
    return boxes;
  }
  //finds all possible boards of tic tac toe and populates the boxes with the moves a computer can make
  public static ArrayList<box> search () {
      ArrayList<box> boxes = new ArrayList<box>();
      int [] num = new int [] {0,0,0,0,0,0,0,0,0};
      int [] MAX = new int [] {2,2,2,2,2,2,2,2,2};
      //ternary counter of a possible way to arrange pieces in 1d
      while(!Arrays.equals(MAX,num)) {
        if (valid(num)) {
          //makes it 2d and then a box if its a valid position
          int [] [] two = make2(num);
          if (!win(two)) {
            boxes.add(new box(two));
          }
        }
        num[num.length-1] ++;
        for (int j = num.length-1; j>0;j--) {
          if(num[j]==3){
            num[j-1] ++;
            num[j] = 0;
          }
        }
      }
    return boxes;
    }

  
  //makes 1d array 2d
  public static int [] [] make2 (int [] num) {
    int [] a = num.clone();
    int count = 0;
    int [] [] two = new int[(int)Math.sqrt(num.length)] [(int)Math.sqrt(num.length)];
    for (int r = 0; r<two.length; r++) {
      for (int c = 0; c<two.length; c++) {
        two[r][c] = a[count];
        count ++;
      }
    }
    return two;
  }
  //checks if a board is a real game of tic tac toe
  public static boolean valid(int[] fds){
    int ones = 0;
    int twos = 0;
    for (int n: fds) {
      if (n==1)
        ones++;
      if (n==2)
        twos++;
    }
    return ones-1==twos;
  }
  //checks if an id is in boxes
  public static boolean containsid (ArrayList<box> boxes, int id) {
    for (int i = 0; i<boxes.size();i++) {
      if (boxes.get(i).getid()==id){
        return true;
      }
    }
    return false;
  }
  //returns index of id in boxes
  public static int idat (ArrayList<box> boxes, int id) {
    for (int i = 0; i<boxes.size();i++) {
      if (boxes.get(i).getid()==id){
        return i;
      }
    }
    return -1;
  }
  //decodes an id into a board
  public static int [] [] decodeid (int i) {
    int [] [] b = new int [3] [3];
    int r = 2;
    int c = 2;
    int ret = 0;
    int factor = 1;
    while (i > 0) {
      b[r][c] = i%3;
      ret += i%3 * factor;
      factor *= 10;
      i/=3;
      if (c==0) {
        c = 2;
        r--;
      }
      else
        c--;
    }
    return b;
  }
  //displays a board to console
  public static void showboard (int [] [] array1) {
    char [] [] array = new char [3] [3];
    for (int r = 0; r<array1.length;r++) {
      for(int c = 0; c<array1.length;c++){
        if (array1[r][c] == 1) {
          array[r][c] = 'X';
        }
        else if (array1[r][c] == 2)
          array[r][c] = 'O';
        else
          array[r][c] = (char) ((r*3+c+1)+'0');
      }
    }
    System.out.println(array[0][0]+"|"+array[0][1]+"|"+array[0][2]);
    System.out.println("-----");
    System.out.println(array[1][0]+"|"+array[1][1]+"|"+array[1][2]);
    System.out.println("-----");
    System.out.println(array[2][0]+"|"+array[2][1]+"|"+array[2][2]);
  }
//prints a box
  public static void showboard (box b) {
    int [] [] array = b.getb();
    for (int[] x : array)
    {
       for (int y : x)
       {
            System.out.print(y + " ");
       }
       System.out.println();
    }
  }
  //finds the id of a given board    
  public static int findid (int [] [] b){
    int count = 8;
    int out = 0;
    for (int r = 0; r<b.length; r++) {
      for (int c = 0; c<b[0].length; c ++) {
        out += b[r][c] * (int)Math.pow(3,count);
        count --;  
      }
    }
    return out;
  }



//tests if a given board has a winner
public static boolean win (int [] [] board) {
  for (int player = 1; player<3;player++) {
    //int player = 2;
    for (int r = 0; r<board.length; r++) {
      if (board[r][0] == player && board[r][1] == player && board[r][2] == player) {
        return true;
      }
    }
    for (int c = 0; c<board.length; c++) {
      if (board[0][c] == player && board[1][c] == player && board[2][c] == player) {
        return true;
      }
    }
    if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
      return true;
    }
    if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
      return true;
    }
  }
  return false;
}
}
