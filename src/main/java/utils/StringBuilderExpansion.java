package utils;

public class StringBuilderExpansion {

    private StringBuilder stringBuilder;

    public StringBuilderExpansion() {
        stringBuilder = new StringBuilder();
    }

    public StringBuilderExpansion append(final Object message) {
        stringBuilder.append(message);
        return this;
    }

    public StringBuilderExpansion append(final Object... messages) {
        for (Object message : messages) {
            stringBuilder.append(message);
        }
        return this;
    }

    public StringBuilderExpansion appendCRLF() {
        stringBuilder.append(StringUtils.CRLF);
        return this;
    }

    public StringBuilderExpansion appendCRLF(final Object message) {
        return append(message).appendCRLF();
    }

    public StringBuilderExpansion appendCRLF(final Object... messages) {
        return append(messages).appendCRLF();
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
