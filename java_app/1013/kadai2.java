public class kadai2{
public static void main(String[] args){
    char[] ch; ch = new char[4];
    ch[0] = 0x5c3e; ch[1] = 0x4e0b; ch[2] = 0x62d3; ch[3] = 0x672a;
    for(int i = 0;i<4;i++){
        System.out.println("文字=" + ch[i] + " code=" + (int)ch[i]);
    }
}
}