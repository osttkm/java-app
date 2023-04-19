// abstract （抽象）クラスはそのままでは使えない
// 子クラスで継承されること前提のクラス
abstract class Shape {
    int color;
    int lineType;
  
    // 子クラスで実装する為にメソッドの名前だけ予約しておく
    // 中身は無し（abstract のメソッドは中身を書けない，書くとエラー）
    // ただし、引数の中身は指定しておく必要がある
    // abstract で親クラス内に予約されたメソッドは
    // 子クラスで必ず実装しなければならない（実装の強制）
    abstract void draw();   
  }
  
  class Polyline extends Shape {
    Point2D[] points;
  
    void draw(){
      System.out.println("具体的な Polyline 特有のの描画方法はここに記述")
    }
  }
  
  class Circle extends Shape {
    Point2D[] center;
    double    radius;
  
    void draw(){
      System.out.println("具体的な Circle 特有のの描画方法はここに記述")
    }
  }
  
  class Rectangle extends Shape {
    Point2D point1;
    Point2D point2;
  
    void draw(){
      System.out.println("具体的な Rectangle 特有のの描画方法はここに記述")
    }
  }
  
  class TestDraw01 {
    public static void main(String[] args) {
      Shape[] shapes = new Shape[5]; // どんな図形でも統一して shapes で管理できる
  
      shapes[0] = new Polyline(); // 型の宣言はShape型で行い実体はPolyline型で作成
      shapes[1] = new Polyline();
      shapes[2] = new Rectangle();
      shapes[3] = new Circle();
      shapes[4] = new Circle();
  
      for (int i = 0; i < shapes.length; i++) {
        shapes[i].draw(); // 描き方は各々違っていても１行で一括して記述できる
      }
    }
  }