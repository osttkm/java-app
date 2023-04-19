import java.util.Comparator;//コンパレータを入手

public class PersonComparator implements Comparator<Player>{
    public int compare(Player p1,Player p2){
     return p1.HearPoint() < p2.HearPoint() ? -1 : 1;
    }
  }