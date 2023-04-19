import java.Arrays.*;
import java.Comparaor.*;
public class test{
    public static void main(String[] arg){
        int [][] array = {
                        {3,67},
                        {1,123},
                        {4,56},
                        {4,89}
                    };
        TheComparator comparator = new TheComparator();
        comparator.setIndex( 1 );
        Arrays.sort( array, comparator );
        System.out.println(array);
    }
}
