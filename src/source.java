import java.util.Scanner;

public class source {
    final static int EASY = 1;
    final static int NORMAL = 2;
    final static int HARD = 3;
    final static int HARDCORE = 4;
    final static private String gameRule = """
            游戏规则：游戏开始：可以选择目标消解三种数字：2048，1024，512。
            游戏开始时，首先会在棋盘上随机生成一个对应数，然后根据数字的大小生成对应数目的2。
            生成规则是：2048是2的11次方，那么棋盘会生成10个2，1024则为9个。
            分裂：一次行动中，根据移动方向，如果可能，棋盘中最小的数（不包括2）会分裂为两个该数的一半。
            消解：一次行动中，会随机地从棋盘中移除一个2。
            游戏失败：如果一次行动中无法进行消解过程，则游戏失败。
            游戏胜利：进行若干次行动，直到棋盘上的所有数被消解。""";

    public static void main(String[] args) {
        int difficulty = 0;
        int firstNum = 0;

        Scanner reader = new Scanner(System.in);
        System.out.println(gameRule);
        System.out.println("请选择难度");
        System.out.println("1.简单 2.普通 3.困难 4.硬核");
        while (difficulty != EASY && difficulty != NORMAL && difficulty != HARD && difficulty != HARDCORE)
            difficulty = reader.nextInt();

        switch (difficulty) {
            case EASY -> firstNum = 512;
            case NORMAL -> firstNum = 1024;
            case HARD -> firstNum = 2048;
            case HARDCORE -> firstNum = 4096;
        }
        Board board = new Board(firstNum);
    }
}