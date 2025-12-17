public enum Semester {
    SEMESTER1("1"),
    SEMESTER2("2"),
    SEMESTER1AND2("1&2");

    private final String stringValue;

    Semester(String string){
        this.stringValue = string;
    }


    public static String asString(Semester semester){
        return semester.stringValue;
    }

    public static Semester fromString(String string) {
        return switch (string){
            case "1" -> SEMESTER1;
            case "2" -> SEMESTER2;
            case "1&2" -> SEMESTER1AND2;
            default -> throw new IllegalArgumentException("Unknown Semester " + string);
        };
    }
}
