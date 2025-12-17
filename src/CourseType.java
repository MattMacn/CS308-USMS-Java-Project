public enum CourseType {
    UNDERGRAD("Undergraduate"),
    POSTGRAD("Postgraduate");

    private final String stringValue;

    CourseType( String string) {
        this.stringValue = string;
    }

    public static String asString(CourseType courseType){
        return courseType.stringValue;
    }

    public static CourseType fromString(String string) {
        return switch (string){
            case "Undergraduate" -> UNDERGRAD;
            case "Postgraduate" -> POSTGRAD;
            default -> throw new IllegalArgumentException("Unknown CourseType: " + string);
        };
    }
}
