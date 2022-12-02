package kolpakovee.Reversy.Model;

public enum Color {
    white("\u001B[0m"),
    cyan("\u001B[36m"),
    gray("\u001B[37m"),
    red("\u001B[31m"),
    green("\u001B[32m"),
    purple("\u001B[35m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}