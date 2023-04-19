
public class DiceGameObjective {
    public static void main(String[] args) {
      int times = Integer.parseInt(args[0]);//コマンドラインの第一引数は勝利回数
      int dice_num = Integer.parseInt(args[1]);//ダイスの出目の設定
      int setting = Integer.parseInt(args[2]);//デフォルトか否かを0/1で判断 0=setting 1=default
      if(setting == 0){
        Judge sato = new Judge();
        Player john = new Player("ジョンさん");
        Player nancy = new Player("ナンシーさん");
        Player george = new Player("ジョージさん");
        Player Tom = new Player("トムさん");
        Player Bill = new Player("ビルさん");
        sato.startDiceGame(john,nancy,george,Tom,Bill,times,dice_num);
      }
      else{
        int def = 0;//オーバーロードのために引数を増やす
        Judge sato = new Judge();
        Player john = new Player("ジョンさん");
        Player nancy = new Player("ナンシーさん");
        Player george = new Player("ジョージさん");
        Player Tom = new Player("トムさん");
        Player Bill = new Player("ビルさん");
        sato.startDiceGame(john,nancy,george,Tom,Bill,3,12,def);
      }
    }
  }