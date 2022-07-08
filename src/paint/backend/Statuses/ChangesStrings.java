package paint.backend.Statuses;

public enum ChangesStrings {
    FILLCOLOR("Fill Color"),
    BORDERCOLOR("Border Color"),
    DELETE("Delete"),
    ADD("Add"),
    ENLARGE("Enlarge"),
    REDUCE("Reduce");

    private String MESSAGE;
    ChangesStrings(String name){
        MESSAGE = name;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }
}
