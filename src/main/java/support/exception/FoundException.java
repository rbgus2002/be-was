package support.exception;

import webserver.response.HttpStatus;

public class FoundException extends HttpException {

    private final String redirectionUrl;

    public FoundException(String redirectionUrl) {
        super(HttpStatus.FOUND);
        this.redirectionUrl = redirectionUrl;
    }

    public String getRedirectionUrl() {
        return redirectionUrl;
    }

}
