import java.util.ArrayList;
public class box {
  private int id;
  private int [] [] board;
  private boolean win;
  private ArrayList<Integer> avaliblemoves;
  public box (int [] [] b) {
    board = new int [b.length] [b[0].length];
    for (int i = 0;i<b.length;i++) {board[i]=b[i].clone();}
    id = 0;
    id = findid(board);
    boolean win = false;
    avaliblemoves = new ArrayList<Integer>();
    //wining board check (probably no longer nessesary)
    int player = 1;
      for (int r = 0; r<board.length; r++) {
        if (board[r][0] == player && board[r][1] == player && board[r][2] == player) {
          win = true;
        }
      }
      for (int c = 0; c<board.length; c++) {
        if (board[0][c] == player && board[1][c] == player && board[2][c] == player) {
          win = true;
        }
      }
      if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
        win = true;
      }
      if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
        win = true;
      }
    
    //find moves only if not a winning board
    if (!win) {
      int count = 0;
      for (int r = 0; r<board.length; r++) {
        for (int c = 0; c<board.length; c ++) {
          if (board[r][c] == 0) {
            avaliblemoves.add(findid(board.clone(),r*10+c));
            count ++;
          }
        }
      }
    }
  }
  //finds id of given board
  public int findid (int [] [] b){
    int count = 8;
    int out = 0;
    for (int r = 0; r<b.length; r++) {
      for (int c = 0; c<b[0].length; c ++) {
        out += b[r][c] * Math.pow(3,count);
        count --;  
      }
    }
    return out;
  }
  //finds id of board with one change
  public int findid (int [] [] st, int move){
    int [] [] b = new int [st.length] [st[0].length];
    for (int i = 0;i<st.length;i++) {b[i]=st[i].clone();}
    b[move/10][move%10] = 2;
    int count = 8;
    int out = 0;
    for (int r = 0; r<b.length; r++) {
      for (int c = 0; c<b[0].length; c ++) {
        out += b[r][c] * Math.pow(3,count);
        count --;  
      }
    }
    //showboard(b);
    return out;
  }
  public ArrayList<Integer> getmoves() {
    return avaliblemoves;
  }
  public void setmove(ArrayList<Integer> arr) {
    avaliblemoves = arr;
  }
  public void removemove (int move) {
    avaliblemoves.remove(move);
  }
  public int makemove(){
    return avaliblemoves.get((int)(Math.random()*avaliblemoves.size()));
  }
  public int getid(){
    return id;
  }
  public void setwin(boolean w){
    win = w;
  }
  public boolean getwin(){
    return win;
  }
  public int[][] getb() {
    return board;
  }
  public void showboard (int [] [] array) {
    for (int[] x : array)
    {
       for (int y : x)
       {
            System.out.print(y + " ");
       }
       System.out.println();
    }
  }


  
}
