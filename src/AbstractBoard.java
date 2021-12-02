import java.util.Arrays;
import java.util.LinkedList;

public class AbstractBoard {
    public int[][] board;
    final private boolean WIN = true;
    final private boolean LOSE = false;
    final private int SIDE_LENGTH = 4;
    private int minNum;
    private int steps = 0;

    AbstractBoard(int firstNum) {

        board = new int[SIDE_LENGTH][SIDE_LENGTH];
        for (int i = 0; i < SIDE_LENGTH; i++)
            for (int j = 0; j < SIDE_LENGTH; j++)
                board[i][j] = 0;

        int ticket = (int) (Math.random() * 16);
        board[ticket / SIDE_LENGTH][ticket % SIDE_LENGTH] = firstNum;
        int twoNum = 0;
        switch (firstNum) {
            case 512 -> twoNum = 8;
            case 1024 -> twoNum = 9;
            case 2048 -> twoNum = 10;
            case 4096 -> twoNum = 12;
        }
        while (twoNum != 0) {
            ticket = (int) (Math.random() * 16);
            if (board[ticket / SIDE_LENGTH][ticket % SIDE_LENGTH] == 0) {
                board[ticket / SIDE_LENGTH][ticket % SIDE_LENGTH] = 2;
                twoNum--;
            }
        }

    }

    public void doAction(direction dir) {
        steps++;
        minNum = getMinNum();

        switch (dir) {
            case UP -> upMove();
            case DOWN -> downMove();
            case LEFT -> leftMove();
            case RIGHT -> rightMove();
        }

        if (!deleteTwo()) {
            gameOver(LOSE);
        } else if (checkWin()) {
            gameOver(WIN);
        }
    }

    public void upMove() {
        LinkedList<Integer> tempRow = new LinkedList<>();
        for (int column = 0; column < SIDE_LENGTH; column++) {
            tempRow.clear();
            for (int row = 0; row < SIDE_LENGTH; row++) {
                if (board[row][column] != 0) {
                    tempRow.add(board[row][column]);
                }
            }
            for (int orderNum = 0; orderNum < SIDE_LENGTH; orderNum++) {
                if (orderNum == tempRow.size()) tempRow.add(0);
                else if (tempRow.get(orderNum) == minNum && tempRow.size() < SIDE_LENGTH) {
                    tempRow.set(orderNum, minNum / 2);
                    tempRow.add(orderNum + 1, minNum / 2);
                    orderNum++;
                }
            }
            for (int row = 0; row < SIDE_LENGTH; row++)
                board[row][column] = tempRow.get(row);
        }
    }

    public void downMove() {
        LinkedList<Integer> tempRow = new LinkedList<>();
        for (int column = 0; column < SIDE_LENGTH; column++) {
            tempRow.clear();
            for (int row = SIDE_LENGTH - 1; row >= 0; row--) {
                if (board[row][column] != 0) {
                    tempRow.add(board[row][column]);
                }
            }
            for (int orderNum = 0; orderNum < SIDE_LENGTH; orderNum++) {
                if (orderNum == tempRow.size()) tempRow.add(0);
                else if (tempRow.get(orderNum) == minNum && tempRow.size() < SIDE_LENGTH) {
                    tempRow.set(orderNum, minNum / 2);
                    tempRow.add(orderNum + 1, minNum / 2);
                    orderNum++;
                }
            }
            for (int row = 0; row < SIDE_LENGTH; row++)
                board[row][column] = tempRow.get(SIDE_LENGTH - row - 1);
        }
    }

    public void leftMove() {
        LinkedList<Integer> tempRow = new LinkedList<>();
        for (int row = 0; row < SIDE_LENGTH; row++) {
            tempRow.clear();
            for (int column = 0; column < SIDE_LENGTH; column++) {
                if (board[row][column] != 0) {
                    tempRow.add(board[row][column]);
                }
            }
            for (int orderNum = 0; orderNum < SIDE_LENGTH; orderNum++) {
                if (orderNum == tempRow.size()) tempRow.add(0);
                else if (tempRow.get(orderNum) == minNum && tempRow.size() < SIDE_LENGTH) {
                    tempRow.set(orderNum, minNum / 2);
                    tempRow.add(orderNum + 1, minNum / 2);
                    orderNum++;
                }
            }
            for (int column = 0; column < SIDE_LENGTH; column++)
                board[row][column] = tempRow.get(column);
        }
    }

    public void rightMove() {
        LinkedList<Integer> tempRow = new LinkedList<>();
        for (int row = 0; row < SIDE_LENGTH; row++) {
            tempRow.clear();
            for (int column = SIDE_LENGTH - 1; column >= 0; column--) {
                if (board[row][column] != 0) {
                    tempRow.add(board[row][column]);
                }
            }
            for (int orderNum = 0; orderNum < SIDE_LENGTH; orderNum++) {
                if (orderNum == tempRow.size()) tempRow.add(0);
                else if (tempRow.get(orderNum) == minNum && tempRow.size() < SIDE_LENGTH) {
                    tempRow.set(orderNum, minNum / 2);
                    tempRow.add(orderNum + 1, minNum / 2);
                    orderNum++;
                }
            }
            for (int column = 0; column < SIDE_LENGTH; column++)
                board[row][column] = tempRow.get(SIDE_LENGTH - 1 - column);
        }
    }

    public int getMinNum() {
        return Arrays.stream(board).flatMapToInt(Arrays::stream).filter(num -> num != 0 && num != 2).filter(num -> num <= 4096).min().orElse(4096);
    }

    public boolean deleteTwo() {
        int twoNum = (int) Arrays.stream(board).flatMapToInt(Arrays::stream).filter(num -> num == 2).count();
        if (twoNum != 0) {
            int orderNum = (int) (Math.random() * twoNum);
            int count = 0;
            for (int num = 0; num < 16; num++) {
                if (board[num / SIDE_LENGTH][num % SIDE_LENGTH] == 2) {
                    if (count == orderNum) {
                        board[num / 4][num % 4] = 0;
                        break;
                    } else count++;
                }
            }
            return true;
        } else return false;
    }

    public boolean checkWin() {
        return Arrays.stream(board).flatMapToInt(Arrays::stream).noneMatch(num -> num != 0);
    }

    public int[] toDatas() {
        int[] datas;
        datas = new int[16];
        int i = 0;
        for (int[] row : board)
            for (int num : row) {
                datas[i] = num;
                i++;
            }
        return datas;
    }

    public void gameOver(boolean result) {
        if (result == LOSE) {
            System.out.println("游戏失败！");
            System.out.println("行动步数：" + steps);
        } else if (result == WIN) {
            System.out.println("游戏胜利！");
        }
        System.exit(0);
    }
}

enum direction {
    UP, DOWN, LEFT, RIGHT,
}