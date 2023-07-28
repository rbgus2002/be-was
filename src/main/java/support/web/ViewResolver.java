package support.web;

import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.instance.DefaultInstanceManager;
import support.web.exception.NotFoundException;
import support.web.view.ErrorView;
import support.web.view.View;
import support.web.view.ViewContainer;
import utils.LoginUtils;
import webserver.Header;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.MIME;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static webserver.WebPageReader.readStringLineByPath;

public abstract class ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);
    private static final String START_TAG = "<%";
    private static final String END_TAG = "%>";
    private static final ErrorView errorView = new ErrorView();

    public static List<String> parseHtml(HttpRequest request, List<String> body) {
        return body.stream()
                .map(line -> parseLoop(line, request))
                .collect(Collectors.toList());
    }

    /**
     * parseSyntax를 변경사항이 없을 때까지 무한 반복한다.
     */
    public static String parseLoop(String syntax, HttpRequest request) {
        String result = syntax;
        String prev = "";
        while (!Objects.equals(prev, result)) {
            prev = result;
            result = parseSyntax(result, request);
        }
        return result;
    }

    /**
     * <% command arguemnt %> 구문으로 해석하여 명령어 처리를 진행한다.
     */
    private static String parseSyntax(String syntax, HttpRequest request) {
        int startIndex = syntax.indexOf(START_TAG);
        int endIndex = syntax.lastIndexOf(END_TAG);

        if (startIndex != -1 && endIndex != -1) {
            String prev = syntax.substring(0, startIndex);
            String next = syntax.substring(endIndex + 2);
            // 명령어 구분 분리
            syntax = syntax.substring(startIndex + 2, endIndex).trim();

            int firstSpaceIndex = syntax.indexOf(" ");
            String command;
            String argument = null;

            if (firstSpaceIndex != -1) {
                command = syntax.substring(0, firstSpaceIndex);
                argument = syntax.substring(firstSpaceIndex + 1);
            } else {
                command = syntax;
            }
            logger.debug("커맨드: {}", command);
            logger.debug("인자: {}", argument);

            return prev + parseCommand(command, argument, request) + next;
        }
        return syntax;
    }

    /**
     * 사전에 정의된 명령어를 기준으로 새로운 결과값으로 변화한다.
     */
    private static String parseCommand(String command, String argument, HttpRequest request) {
        if ("SESSION.GET_USERNAME".equals(command)) {
            Session loginSession = LoginUtils.getLoginSession(request);
            return LoginUtils.getLoginSession(request) != null ? loginSession.getUser().getName() : "";
        } else if ("SESSION.IS_PRESENT".equals(command)) {
            return LoginUtils.getLoginSession(request) != null ? argument : "";
        } else if ("SESSION.IS_EMPTY".equals(command)) {
            return LoginUtils.getLoginSession(request) != null ? "" : argument;
        }
        return START_TAG + " " + command + " " + argument + " " + END_TAG;
    }

    public static HttpEntity buildView(HttpRequest request, HttpResponse response, ModelAndView modelAndView) throws NotFoundException, IOException {
        ViewContainer viewFactory = DefaultInstanceManager.getInstanceManager().getInstance("ViewContainer", ViewContainer.class);
        String path = modelAndView.getViewName();
        View view = viewFactory.getViewByName(path);

        if (view != null) {
            return ViewResolver.buildView(request, response, view, modelAndView.getModel());
        } else {
            return ViewResolver.buildStaticView(request, response, path);
        }
    }

    private static HttpEntity buildStaticView(HttpRequest request, HttpResponse response, String path) throws NotFoundException, IOException {
        List<String> body = readStringLineByPath(path);
        String extension = path.substring(path.lastIndexOf("."));
        if (".html".equals(extension)) {
            body = parseHtml(request, body);
        }
        StringBuilder stringBuilder = new StringBuilder();
        body.forEach(stringBuilder::append);
        byte[] bodyBytes = stringBuilder.toString().getBytes();
        response.setBody(bodyBytes);

        Header header = new Header();
        header.setContentType(MIME.getContentType(extension))
                .setContentLength(String.valueOf(bodyBytes.length));
        return new HttpEntity(HttpStatus.OK, header);
    }

    public static HttpEntity buildView(HttpRequest request, HttpResponse response, View view, Model model) {
        byte[] bodyBytes = view.render(request, response, model).getBytes();
        response.setBody(bodyBytes);

        Header header = new Header();
        header.setContentType(MIME.getContentType(".html"))
                .setContentLength(String.valueOf(bodyBytes.length));
        return new HttpEntity(HttpStatus.OK, header);
    }

    public static void buildErrorView(HttpRequest request, HttpResponse response) {
        buildView(request, response, errorView, null);
    }

}
