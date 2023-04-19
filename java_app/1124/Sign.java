public class Sign {
    Sign(){}
    public double cal(int mode,double num1, double num2){
        if(mode == 1){ //足し算
            return (num1+num2);
        }
        else if(mode == 2){ //引き算
            return  num1-num2;
        }
        else if(mode == 3){ //掛け算
            return num1*num2;
        }
        else if(mode == 4){ //割り算
            return  num1/num2;
        }
        else if(mode==0){ //ただ＝を押しただけの時の処理
            return num1;
        }
        else{
            return 0; //計算法について範囲外の時はエラー値として０を返す
        }
    }

    public double ano_cal(int cal_flag,double num1,double num2){

        if(cal_flag == 1){ //足し算
            return (num1+num2);
        }
        else if(cal_flag == 2){ //引き算
            return  num1-num2;
        }
        else if(cal_flag == 3){ //掛け算
            return num1*num2;
        }
        else if(cal_flag == 4){ //割り算
            return  num1/num2;
        }
        else if(cal_flag==0){ //ただ＝を押しただけの時の処理
            return num1;
        }
        else{
            return 0; //計算法について範囲外の時はエラー値として０を返す
        }
    }
}
