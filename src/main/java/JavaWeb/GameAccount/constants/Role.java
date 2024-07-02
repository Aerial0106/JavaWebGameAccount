package JavaWeb.GameAccount.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ADMIN(2),
    USER(1);
    public final long value;
}