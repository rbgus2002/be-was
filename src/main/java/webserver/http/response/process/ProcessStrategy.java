package webserver.http.response.process;

public enum ProcessStrategy {
    APPLICATION(new ApplicationProcessStrategy()),
    TEMPLATE(new TemplateProcessStrategy()),
    STATIC(new StaticContentProcessStrategy());

    public final ContentProcessStrategy strategy;

    ProcessStrategy(final ContentProcessStrategy contentProcessStrategy) {
        this.strategy = contentProcessStrategy;
    }
}
