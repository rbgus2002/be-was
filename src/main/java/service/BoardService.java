package service;

import model.Board;

import java.util.Map;

public class BoardService {
    private final String WRITER = "writer";
    private final String TITLE = "title";
    private final String CONTENTS = "contents";

    public void createQnA(Map<String, String> qnaInfo, String userId) {
        Board qna = new Board(qnaInfo.get(WRITER), qnaInfo.get(TITLE), qnaInfo.get(CONTENTS));

    }

}
