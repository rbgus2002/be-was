package support.web.view;

import support.annotation.Component;
import support.web.Model;
import utils.StringBuilderExpansion;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

@Component
public class PostShowView implements View {

    @Override
    public String getName() {
        return "/post/show";
    }

    @Override
    public String render(HttpRequest request, HttpResponse response, Model model) {
        StringBuilderExpansion stringBuilder = new StringBuilderExpansion();
        stringBuilder
                .appendCRLF("<!DOCTYPE html>")
                .appendCRLF("<html lang=\"kr\">")
                .appendCRLF("<head>")
                .appendCRLF("    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">")
                .appendCRLF("    <meta charset=\"utf-8\">")
                .appendCRLF("    <title>SLiPP Java Web Programming</title>")
                .appendCRLF("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">")
                .appendCRLF("    <link href=\"../css/bootstrap.min.css\" rel=\"stylesheet\">")
                .appendCRLF("    <!--[if lt IE 9]>")
                .appendCRLF("    <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>")
                .appendCRLF("    <![endif]-->")
                .appendCRLF("    <link href=\"../css/styles.css\" rel=\"stylesheet\">")
                .appendCRLF("</head>")
                .appendCRLF("<body>")
                .appendCRLF("<nav class=\"navbar navbar-fixed-top header\">")
                .appendCRLF("    <div class=\"col-md-12\">")
                .appendCRLF("        <div class=\"navbar-header\">")
                .appendCRLF("")
                .appendCRLF("            <a href=\"../index\" class=\"navbar-brand\">SLiPP</a>")
                .appendCRLF("            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">")
                .appendCRLF("                <i class=\"glyphicon glyphicon-search\"></i>")
                .appendCRLF("            </button>")
                .appendCRLF("")
                .appendCRLF("        </div>")
                .appendCRLF("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">")
                .appendCRLF("            <form class=\"navbar-form pull-left\">")
                .appendCRLF("                <div class=\"input-group\" style=\"max-width:470px;\">")
                .appendCRLF("                    <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">")
                .appendCRLF("                    <div class=\"input-group-btn\">")
                .appendCRLF("                        <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>")
                .appendCRLF("                    </div>")
                .appendCRLF("                </div>")
                .appendCRLF("            </form>")
                .appendCRLF("            <ul class=\"nav navbar-nav navbar-right\">")
                .appendCRLF("                <li>")
                .appendCRLF("                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>")
                .appendCRLF("                    <ul class=\"dropdown-menu\">")
                .appendCRLF("                        <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>")
                .appendCRLF("                        <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>")
                .appendCRLF("                    </ul>")
                .appendCRLF("                </li>")
                .appendCRLF("                <li><a href=\"../user/list\"><i class=\"glyphicon glyphicon-user\"></i></a></li>")
                .appendCRLF("            </ul>")
                .appendCRLF("        </div>")
                .appendCRLF("    </div>")
                .appendCRLF("</nav>")
                .appendCRLF("<div class=\"navbar navbar-default\" id=\"subnav\">")
                .appendCRLF("    <div class=\"col-md-12\">")
                .appendCRLF("        <div class=\"navbar-header\">")
                .appendCRLF("            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>")
                .appendCRLF("            <ul class=\"nav dropdown-menu\">")
                .appendCRLF("                <li><a href=\"../user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>")
                .appendCRLF("                <li class=\"nav-divider\"></li>")
                .appendCRLF("                <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>")
                .appendCRLF("            </ul>")
                .appendCRLF("            ")
                .appendCRLF("            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">")
                .appendCRLF("            	<span class=\"sr-only\">Toggle navigation</span>")
                .appendCRLF("            	<span class=\"icon-bar\"></span>")
                .appendCRLF("            	<span class=\"icon-bar\"></span>")
                .appendCRLF("            	<span class=\"icon-bar\"></span>")
                .appendCRLF("            </button>            ")
                .appendCRLF("        </div>")
                .appendCRLF("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">")
                .appendCRLF("            <ul class=\"nav navbar-nav navbar-right\">")
                .appendCRLF("                <li class=\"active\"><a href=\"../index\">Posts</a></li>")
                .appendCRLF("                <li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>")
                .appendCRLF("                <li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>")
                .appendCRLF("                <li><a href=\"#\" role=\"button\">로그아웃</a></li>")
                .appendCRLF("                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>")
                .appendCRLF("            </ul>")
                .appendCRLF("        </div>")
                .appendCRLF("    </div>")
                .appendCRLF("</div>")
                .appendCRLF("")
                .appendCRLF("<div class=\"container\" id=\"main\">")
                .appendCRLF("    <div class=\"col-md-12 col-sm-12 col-lg-12\">")
                .appendCRLF("        <div class=\"panel panel-default\">")
                .appendCRLF("          <header class=\"qna-header\">")
                .appendCRLF("              <h2 class=\"qna-title\">", model.getAttribute("title"), "</h2>")
                .appendCRLF("          </header>")
                .appendCRLF("          <div class=\"content-main\">")
                .appendCRLF("              <article class=\"article\">")
                .appendCRLF("                  <div class=\"article-header\">")
                .appendCRLF("                      <div class=\"article-header-thumb\">")
                .appendCRLF("                          <img src=\"https://graph.facebook.com/v2.3/100000059371774/picture\" class=\"article-author-thumb\" alt=\"\">")
                .appendCRLF("                      </div>")
                .appendCRLF("                      <div class=\"article-header-text\">")
                .appendCRLF("                          <a href=\"/users/92/", model.getAttribute("writer"), "\" class=\"article-author-name\">", model.getAttribute("writer"), "</a>")
                .appendCRLF("                          <a href=\"/questions/413\" class=\"article-header-time\" title=\"퍼머링크\">")
                .appendCRLF("                              ", model.getAttribute("createDateTime"))
                .appendCRLF("                              <i class=\"icon-link\"></i>")
                .appendCRLF("                          </a>")
                .appendCRLF("                      </div>")
                .appendCRLF("                  </div>")
                .appendCRLF("                  <div class=\"article-doc\">")
                .appendCRLF(model.getAttribute("content"))
                .appendCRLF("                  </div>")
                .appendCRLF("                  <div class=\"article-utils\">")
                .appendCRLF("                      <ul class=\"article-utils-list\">")
                .appendCRLF("                          <li>")
                .appendCRLF("                              <a class=\"link-modify-article\" href=\"/questions/423/form\">수정</a>")
                .appendCRLF("                          </li>")
                .appendCRLF("                          <li>")
                .appendCRLF("                              <form class=\"form-delete\" action=\"/questions/423\" method=\"POST\">")
                .appendCRLF("                                  <input type=\"hidden\" name=\"_method\" value=\"DELETE\">")
                .appendCRLF("                                  <button class=\"link-delete-article\" type=\"submit\">삭제</button>")
                .appendCRLF("                              </form>")
                .appendCRLF("                          </li>")
                .appendCRLF("                          <li>")
                .appendCRLF("                              <a class=\"link-modify-article\" href=\"/index\">목록</a>")
                .appendCRLF("                          </li>")
                .appendCRLF("                      </ul>")
                .appendCRLF("                  </div>")
                .appendCRLF("              </article>")
                .appendCRLF("")
                .appendCRLF("              <div class=\"qna-comment\">")
                .appendCRLF("                  <div class=\"qna-comment-slipp\">")
                .appendCRLF("                      <p class=\"qna-comment-count\"><strong>2</strong>개의 의견</p>")
                .appendCRLF("                      <div class=\"qna-comment-slipp-articles\">")
                .appendCRLF("")
                .appendCRLF("                          <article class=\"article\" id=\"answer-1405\">")
                .appendCRLF("                              <div class=\"article-header\">")
                .appendCRLF("                                  <div class=\"article-header-thumb\">")
                .appendCRLF("                                      <img src=\"https://graph.facebook.com/v2.3/1324855987/picture\" class=\"article-author-thumb\" alt=\"\">")
                .appendCRLF("                                  </div>")
                .appendCRLF("                                  <div class=\"article-header-text\">")
                .appendCRLF("                                      <a href=\"/users/1/자바지기\" class=\"article-author-name\">자바지기</a>")
                .appendCRLF("                                      <a href=\"#answer-1434\" class=\"article-header-time\" title=\"퍼머링크\">")
                .appendCRLF("                                          2016-01-12 14:06")
                .appendCRLF("                                      </a>")
                .appendCRLF("                                  </div>")
                .appendCRLF("                              </div>")
                .appendCRLF("                              <div class=\"article-doc comment-doc\">")
                .appendCRLF("                                  <p>이 글만으로는 원인 파악하기 힘들겠다. 소스 코드와 설정을 단순화해서 공유해 주면 같이 디버깅해줄 수도 있겠다.</p>")
                .appendCRLF("                              </div>")
                .appendCRLF("                              <div class=\"article-utils\">")
                .appendCRLF("                                  <ul class=\"article-utils-list\">")
                .appendCRLF("                                      <li>")
                .appendCRLF("                                          <a class=\"link-modify-article\" href=\"/questions/413/answers/1405/form\">수정</a>")
                .appendCRLF("                                      </li>")
                .appendCRLF("                                      <li>")
                .appendCRLF("                                          <form class=\"delete-answer-form\" action=\"/questions/413/answers/1405\" method=\"POST\">")
                .appendCRLF("                                              <input type=\"hidden\" name=\"_method\" value=\"DELETE\">")
                .appendCRLF("                                              <button type=\"submit\" class=\"delete-answer-button\">삭제</button>")
                .appendCRLF("                                          </form>")
                .appendCRLF("                                      </li>")
                .appendCRLF("                                  </ul>")
                .appendCRLF("                              </div>")
                .appendCRLF("                          </article>")
                .appendCRLF("                          <article class=\"article\" id=\"answer-1406\">")
                .appendCRLF("                              <div class=\"article-header\">")
                .appendCRLF("                                  <div class=\"article-header-thumb\">")
                .appendCRLF("                                      <img src=\"https://graph.facebook.com/v2.3/1324855987/picture\" class=\"article-author-thumb\" alt=\"\">")
                .appendCRLF("                                  </div>")
                .appendCRLF("                                  <div class=\"article-header-text\">")
                .appendCRLF("                                      <a href=\"/users/1/자바지기\" class=\"article-author-name\">자바지기</a>")
                .appendCRLF("                                      <a href=\"#answer-1434\" class=\"article-header-time\" title=\"퍼머링크\">")
                .appendCRLF("                                          2016-01-12 14:06")
                .appendCRLF("                                      </a>")
                .appendCRLF("                                  </div>")
                .appendCRLF("                              </div>")
                .appendCRLF("                              <div class=\"article-doc comment-doc\">")
                .appendCRLF("                                  <p>이 글만으로는 원인 파악하기 힘들겠다. 소스 코드와 설정을 단순화해서 공유해 주면 같이 디버깅해줄 수도 있겠다.</p>")
                .appendCRLF("                              </div>")
                .appendCRLF("                              <div class=\"article-utils\">")
                .appendCRLF("                                  <ul class=\"article-utils-list\">")
                .appendCRLF("                                      <li>")
                .appendCRLF("                                          <a class=\"link-modify-article\" href=\"/questions/413/answers/1405/form\">수정</a>")
                .appendCRLF("                                      </li>")
                .appendCRLF("                                      <li>")
                .appendCRLF("                                          <form class=\"delete-answer-form\" action=\"/questions/413/answers/1405\" method=\"POST\">")
                .appendCRLF("                                              <input type=\"hidden\" name=\"_method\" value=\"DELETE\">")
                .appendCRLF("                                              <button type=\"submit\" class=\"delete-answer-button\">삭제</button>")
                .appendCRLF("                                          </form>")
                .appendCRLF("                                      </li>")
                .appendCRLF("                                  </ul>")
                .appendCRLF("                              </div>")
                .appendCRLF("                          </article>")
                .appendCRLF("                          <form class=\"answer-form\">")
                .appendCRLF("                              <div class=\"form-group\" style=\"padding:14px;\">")
                .appendCRLF("                                  <textarea class=\"form-control\" placeholder=\"Update your status\"></textarea>")
                .appendCRLF("                              </div>")
                .appendCRLF("                              <button class=\"btn btn-success pull-right\" type=\"button\">답변하기</button>")
                .appendCRLF("                              <div class=\"clearfix\" />")
                .appendCRLF("                          </form>")
                .appendCRLF("                      </div>")
                .appendCRLF("                  </div>")
                .appendCRLF("              </div>")
                .appendCRLF("          </div>")
                .appendCRLF("        </div>")
                .appendCRLF("    </div>")
                .appendCRLF("</div>")
                .appendCRLF("")
                .appendCRLF("<script type=\"text/template\" id=\"answerTemplate\">")
                .appendCRLF("	<article class=\"article\">")
                .appendCRLF("		<div class=\"article-header\">")
                .appendCRLF("			<div class=\"article-header-thumb\">")
                .appendCRLF("				<img src=\"https://graph.facebook.com/v2.3/1324855987/picture\" class=\"article-author-thumb\" alt=\"\">")
                .appendCRLF("			</div>")
                .appendCRLF("			<div class=\"article-header-text\">")
                .appendCRLF("				<a href=\"#\" class=\"article-author-name\">{0}</a>")
                .appendCRLF("				<div class=\"article-header-time\">{1}</div>")
                .appendCRLF("			</div>")
                .appendCRLF("		</div>")
                .appendCRLF("		<div class=\"article-doc comment-doc\">")
                .appendCRLF("			{2}")
                .appendCRLF("		</div>")
                .appendCRLF("		<div class=\"article-utils\">")
                .appendCRLF("		<ul class=\"article-utils-list\">")
                .appendCRLF("			<li>")
                .appendCRLF("				<a class=\"link-modify-article\" href=\"/api/questions/{3}/answers/{4}/form\">수정</a>				")
                .appendCRLF("			</li>")
                .appendCRLF("			<li>")
                .appendCRLF("				<form class=\"delete-answer-form\" action=\"/api/questions/{3}/answers/{4}\" method=\"POST\">")
                .appendCRLF("					<input type=\"hidden\" name=\"_method\" value=\"DELETE\">")
                .appendCRLF("                     <button type=\"submit\" class=\"delete-answer-button\">삭제</button>")
                .appendCRLF("				</form>")
                .appendCRLF("			</li>")
                .appendCRLF("		</ul>")
                .appendCRLF("		</div>")
                .appendCRLF("	</article>")
                .appendCRLF("</script>")
                .appendCRLF("")
                .appendCRLF("<!-- script references -->")
                .appendCRLF("<script src=\"../js/jquery-2.2.0.min.js\"></script>")
                .appendCRLF("<script src=\"../js/bootstrap.min.js\"></script>")
                .appendCRLF("<script src=\"../js/scripts.js\"></script>")
                .appendCRLF("	</body>")
                .appendCRLF("</html>");

        return stringBuilder.toString();
    }

}
