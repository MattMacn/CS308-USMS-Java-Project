public enum View {
    WELCOME("USMS - Welcome"),
    LOGIN("USMS - Log In"),
    SIGNUP("USMS - Sign Up"),
    STUDENT("USMS - Student Homepage"),
    LECTURER("USMS - Lecturer Homepage"),
    MANAGER("USMS - Manager Homepage");

    private final String windowTitle;

    View(String title) {
        this.windowTitle = title;
    }

    public String getWindowTitle(){
        return windowTitle;
    }
}
