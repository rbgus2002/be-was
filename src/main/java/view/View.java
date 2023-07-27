package view;

import exception.BadRequestException;
import exception.SessionIdException;
import http.HttpRequest;
import model.Board;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static db.BoardDatabase.findAllBoards;
import static db.BoardDatabase.findBoardById;
import static db.Database.findAll;
import static db.Database.findUserById;
import static db.SessionStorage.*;
import static exception.ExceptionList.*;
import static http.FilePath.*;
import static utils.FileUtils.TEMPLATES_DIRECTORY;

public class View {

    public String getDynamicView(HttpRequest httpRequest, String filePath) {
        switch (filePath) {
            case INDEX:
                return getIndexView(httpRequest);
            case LIST:
                return getListView(httpRequest);
            case PROFILE:
                return getProfileView(httpRequest);
            case SHOW:
                return getShowView(httpRequest);
            default:
                throw new BadRequestException(INVALID_URI);
        }
    }

    public String getErrorView(String errorMessage) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>Error Page</title>" +
                "</head>" +
                "<body>" +
                "<h1>Error: " + errorMessage + "</h1>" +
                "</body>" +
                "</html>";
    }

    private String getProfileView(HttpRequest httpRequest) {
        String sessionId = httpRequest.getSessionId();
        if (!isSessionValid(sessionId)) {
            throw new SessionIdException(INVALID_SESSION_ID);
        }
        String userId = findUserIdBySessionId(sessionId);
        User user = findUserById(userId);

        StringBuilder profileBuilder = new StringBuilder();
        File indexFile = new File(TEMPLATES_DIRECTORY + PROFILE);
        String line;
        try {
            BufferedReader index = new BufferedReader(new FileReader(indexFile));
            while ((line = index.readLine()) != null) {
                if (line.contains("<h4 class=\"media-heading\">자바지기</h4>")) {
                    profileBuilder.append("<h4 class=\"media-heading\">").append(user.getName()).append("</h4>");
                }
                else if (line.contains("<a href=\"#\" class=\"btn btn-xs btn-default\"><span class=\"glyphicon glyphicon-envelope\"></span>&nbsp;javajigi@slipp.net</a>")) {
                    profileBuilder.append("<a href=\"#\" class=\"btn btn-xs btn-default\"><span class=\"glyphicon glyphicon-envelope\"></span>&nbsp;").append(user.getEmail()).append("</a>");
                }
                else profileBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return profileBuilder.toString();
    }

    private String getListView(HttpRequest httpRequest) {
        String sessionId = httpRequest.getSessionId();
        if (!isSessionValid(sessionId)) {
            throw new SessionIdException(INVALID_SESSION_ID);
        }

        StringBuilder userList = new StringBuilder("<tbody>\n");
        int i = 1;
        for (User user : findAll()) {
            userList.append("<tr>\n")
                    .append("<th scope=\"row\">").append(i++)
                    .append("</th> <td>").append(user.getUserId())
                    .append("</td> <td>").append(user.getName())
                    .append("</td> <td>").append(user.getEmail())
                    .append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n")
                    .append("</tr>\n");
        }
        userList.append("</tbody>\n");

        StringBuilder listBuilder = new StringBuilder();
        File indexFile = new File(TEMPLATES_DIRECTORY + LIST);
        String line;
        boolean tbodyFlag = true;
        try {
            BufferedReader index = new BufferedReader(new FileReader(indexFile));
            while ((line = index.readLine()) != null) {
                if (line.contains("<tbody>")) {
                    tbodyFlag = false;
                }
                if (tbodyFlag) {
                    listBuilder.append(line);
                }
                if (line.contains("</tbody>")) {
                    tbodyFlag = true;
                    listBuilder.append(userList);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listBuilder.toString();
    }

    private String getIndexView(HttpRequest httpRequest) {
        String sessionId = httpRequest.getSessionId();

        StringBuilder boardList = new StringBuilder("<ul class=\"list\">\n");
        for (Board board : findAllBoards()) {
            boardList.append("<li>\n").append("<div class=\"wrap\">\n").append("<div class=\"main\">\n")
                    .append("<strong class=\"subject\">")
                    .append("<a href=\"./qna/show.html?index=").append(board.getIndex()).append("\">").append(board.getTitle()).append("</a>")
                    .append("</strong>\n").append("<div class=\"auth-info\">\n").append("<i class=\"icon-add-comment\"></i>\n")
                    .append("<span class=\"time\">").append(board.getTime()).append("&nbsp;</span>")
                    .append("<a href=\"./user/profile.html\" class=\"author\">").append(board.getWriter()).append("</a>")
                    .append("</div>\n").append("<div class=\"reply\" title=\"댓글\">\n").append("<i class=\"icon-reply\"></i>")
                    .append("<span class=\"point\">").append(board.getIndex()).append("</span>")
                    .append("</div>\n").append("</div>\n").append("</div>\n").append("</li>");
        }
        boardList.append("</ul>\n");

        StringBuilder indexBuilder = new StringBuilder();
        File indexFile = new File(TEMPLATES_DIRECTORY + INDEX);
        String line;
        boolean listFlag = true;
        try {
            BufferedReader index = new BufferedReader(new FileReader(indexFile));
            while ((line = index.readLine()) != null) {
                if (line.contains("<ul class=\"list\">")) {
                    listFlag = false;
                }
                if (line.contains("user-name")) {
                    if (isSessionValid(sessionId)) {
                        indexBuilder.append("<li id=\"user-name\"><br/>").append(findUserIdBySessionId(sessionId)).append("&nbsp;님</li>");
                    }
                    continue;
                }
                if (listFlag) {
                    indexBuilder.append(line);
                }
                if (line.contains("<!-- list finish -->")) {
                    listFlag = true;
                    indexBuilder.append(boardList);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return indexBuilder.toString();
    }

    private String getShowView(HttpRequest httpRequest) {
        if (!isSessionValid(httpRequest.getSessionId())) {
            throw new SessionIdException(INVALID_SESSION_ID);
        }

        for (String key : httpRequest.getQueryString().keySet())
            System.out.println("keykey : " + key);
        String boardIndex = httpRequest.getQueryString().get("index");
        Board board = findBoardById(Long.valueOf(boardIndex));

        StringBuilder boardDetail = new StringBuilder();
        File showFile = new File(TEMPLATES_DIRECTORY + SHOW);
        String line;
        try {
            BufferedReader index = new BufferedReader(new FileReader(showFile));
            while ((line = index.readLine()) != null) {
                if (line.contains("qna-title")) {
                    boardDetail.append("<h2 class=\"qna-title\">").append(board.getTitle()).append("</h2>");
                }
                else if (line.contains("kimmunsu")) {
                    boardDetail.append("<a href=\"/users/92/kimmunsu\" class=\"article-author-name\">").append(board.getWriter()).append("</a>");
                }
                else if (line.contains("board-time")) {
                    boardDetail.append("<a href=\"/questions/413\" class=\"article-header-time\" title=\"퍼머링크\" id=\"board-time\">").append(board.getTime());
                }
                else if (line.contains("board-contents")) {
                    boardDetail.append("<p id=\"board-contents\">").append(board.getContents()).append("</p>\n");
                }
                else boardDetail.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return boardDetail.toString();

    }
}
