package db;

import com.google.common.collect.Maps;
import model.Board;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class BoardDatabase {
    private static Map<Long, Board> boards = Maps.newConcurrentMap();

    public static void addBoard(Board board) {
        boards.put(board.getIndex(), board);
    }

    public static Board findBoardById(Long index) {
        return boards.get(index);
    }

    public static Collection<Board> findAllBoards() {
        Map<Long, Board> treeMap = new TreeMap<>(Collections.reverseOrder());
        treeMap.putAll(boards);
        return treeMap.values();
    }

    public static int getBoardSize() {
        return boards.size();
    }

    public static void clearBoards() {
        boards.clear();
    }

}
