package net.okur.reagobs.dto.output;

import java.io.Serializable;

/**
 * DTO for {@link net.okur.reagobs.entity.User}
 */
public record UserOutput(Long id, String username, String email, Boolean active) implements Serializable {
}