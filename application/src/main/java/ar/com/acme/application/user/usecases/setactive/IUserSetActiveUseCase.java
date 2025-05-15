package ar.com.acme.application.user.usecases.setactive;

import java.util.UUID;

public interface IUserSetActiveUseCase {
    public void setActive(UUID userId, boolean active);
}
