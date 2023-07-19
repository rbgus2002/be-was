package controller;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;

public interface Controller {

	public String process(HttpRequest request, HttpResponse response);
}
