package webserver.request;

import utils.StringUtils;

public class Parameter extends KeyValue {

    protected Parameter() {
        super(false);
    }

    protected Parameter(String parameterString) {
        super(parameterString, false);
    }

    @Override
    public String toString() {
        return (this.size() != 0 ? StringUtils.NEW_LINE + "Body : " : "") + super.toString();
    }

}
