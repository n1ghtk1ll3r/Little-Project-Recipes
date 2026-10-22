package Project.APIs.Domain.Model.Enuns;

public enum RecipeTipe {
    SALTY("salgado"),
    SWEET("doce");

    private final String value;

    RecipeTipe(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}