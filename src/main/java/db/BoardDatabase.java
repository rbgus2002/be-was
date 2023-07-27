package db;

import com.google.common.collect.Maps;
import model.Board;

import java.util.Collection;
import java.util.Map;

public class BoardDatabase {
    private static Map<Long, Board> boards = Maps.newConcurrentMap();

    public static void addBoard(Board board) {
        boards.put(board.getIndex(), board);
    }

    public static Board findBoardById(Long index) {
        return boards.get(index);
    }

    public static Collection<Board> findAllBoards() {
        return boards.values();
    }

    public static void clearBoards() {
        boards.clear();
    }

}
