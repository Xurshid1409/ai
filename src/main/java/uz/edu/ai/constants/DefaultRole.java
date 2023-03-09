package uz.edu.ai.constants;

public enum DefaultRole {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MODERATOR("ROLE_MODERATOR"),

    ROLE_SUPERADMIN("ROLE_SUPERADMIN");
    private final String message;

    DefaultRole(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
