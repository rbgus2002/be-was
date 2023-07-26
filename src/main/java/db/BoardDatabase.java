package db;

import model.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardDatabase {
    private static List<Board> boards = new ArrayList<>();

    public static void addBoard(Board board, String userId) {
        boards.add(board);
    }

}
