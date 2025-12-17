public enum ModuleResult {
    EXAM("Exam"),
    LAB("Lab"),
    LABANDEXAM("Lab & Exam"),
    OTHER("Other");

    private String internalValue;

    ModuleResult(String string) {
        this.internalValue = string;
    }

    public static String asString(ModuleResult result){
        return result.internalValue;
    }

    public static ModuleResult fromString(String string) {
        return switch (string){
            case "Exam" -> EXAM;
            case "Lab" -> LAB;
            case "Lab & Exam" -> LABANDEXAM;
            default -> OTHER;
        };
    }
}
