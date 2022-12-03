package kolpakovee.Reversy.Model;

/**
 * Пеечисление для удобного хранения цветов
 */
public enum Color {
    white("\u001B[0m"),
    cyan("\u001B[36m"),
    gray("\u001B[37m"),
    red("\u001B[31m"),
    green("\u001B[32m"),
    purple("\u001B[35m");

    private final String code;

    /**
     * Устанавливает цвет
     * @param code код
     */
    Color(String code) {
        this.code = code;
    }

    /**
     * Возвращает код цвета
     * @return код цвета
     */
    public String getCode() {
        return code;
    }
}
