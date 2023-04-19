/**
 * サイコロ転がしゲームのプレイヤークラス
 */
public class Player{
    /*■■■ メンバー変数部 ■■■*/  
    private String name_;      // プレイヤーの名前
    private int winCount_ = 0; // プレイヤーの勝った回数
    private int diceSpot_;     // プレイヤーの出した目
  
    /*■■■ コンストラクタ部 ■■■*/  
    public Player(String name) {
       this.name_ = name;
    }
    /*■■■ メソッド部 ■■■*/
    /**
     * サイコロを振るメソッド
     * メソッドが呼び出される度に乱数でサイコロを振る
     *
     * @return サイコロの目
     */
    public int rollTheDice(int i) {
      diceSpot_ = (int)Math.ceil(Math.random()*i);
      return diceSpot_;
    }
    
    public int HearPoint(){ //サイコロを振った後に、得点を得る関数
      return diceSpot_;
    }

    /**
     * 審判から勝敗を聞くメソッド
     *
     * @param result true:勝ち,false:負け
     */
    public void notifyResult(boolean result) {
      if (result == true){
        winCount_ += 1; // 勝った場合は勝ち数を加算する
      }
    }
  
    /**
     * 自分の勝った回数を答えるメソッド
     *
     * @return 勝った回数
     */
    public int getWinCount() {
      return winCount_;
    }
  
    /**
     * 自分の名前を答えるメソッド
     * 
     * @return 名前
     */
    public String getName() {
      return name_;
    }
  }