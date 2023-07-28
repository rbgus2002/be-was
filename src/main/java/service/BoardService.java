package service;

import model.Board;

import java.util.Map;

import static db.BoardDatabase.addBoard;

public class BoardService {
    private final String WRITER = "writer";
    private final String TITLE = "title";
    private final String CONTENTS = "contents";

    public void createBoard(Map<String, String> boardInfo) {
        Board board = new Board(boardInfo.get(WRITER), boardInfo.get(TITLE), boardInfo.get(CONTENTS));
        addBoard(board);
    }

}
