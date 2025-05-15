package ar.com.acme.application.user.mappers;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import ar.com.acme.application.phone.mappers.PhoneOutMapper;
import ar.com.acme.model.user.User;

public record UserOutMapper(UUID id,
                              String name,
                              String email,
                              LocalDateTime created,
                              LocalDateTime modified,
                              Boolean active,
                              String token,
                              LocalDateTime lastLogin,
                              Collection<PhoneOutMapper> phones) {
    public static UserOutMapper fromUser(User user) {
        return new UserOutMapper(user.getId(),
                                   user.getName(),
                                   user.getEmail(),
                                   user.getCreated(),
                                   user.getModified(),
                                   user.getActive(),
                                   (user.getToken() != null ? user.getToken().toString() : null),
                                   user.getLastLogin(),
                                   user.getPhones().stream().map(PhoneOutMapper::fromPhone).collect(Collectors.toList()));
    }
 }
