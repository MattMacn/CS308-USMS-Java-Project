public enum Award {

    AWARD("Award"),
    RESIT("Resit"),
    WITHDRAW("Withdraw");

    private final String stringValue;

    Award(String string){
        this.stringValue = string;
    }


    public static String asString(Award award){
        return award.stringValue;
    }

    public static Award fromString(String string) {
        return switch (string){
            case "Award" -> AWARD;
            case "Resit" -> RESIT;
            case "Withdraw" -> WITHDRAW;
            default -> throw new IllegalArgumentException("Unknown Award " + string);
        };
    }
}
