package ar.com.acme.application.user.usecases.setactive;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.com.acme.commons.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Constants.URL_CONTROLLER_USER)
@RequiredArgsConstructor
public class UserSetActiveController {
    private final IUserSetActiveUseCase setActiveUseCase;

    @Operation(summary = "Modifica el estado de activación de un Usuario",
               description = "Permite modificar el estado de activación de un Usuario, activándolo (true) o desactivándolo (false).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Teléfono agregado al Usuario"),
        @ApiResponse(responseCode = "404", description = "No se ha encontrado el Usuario"),
        @ApiResponse(responseCode = "401", description = "El cliente no tiene permisos suficiente para la operación.")})
    @PutMapping("/{userId}/active/{active}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void setActive(
        @Parameter(
            name = "userId",
            description = "ID del Usuario al que se agregará el teléfono",
            example = "1a245fd4-35f4-40d3-b5b4-3d4d30a66e02")
        @PathVariable("userId") UUID userId,
        @Parameter(
            name = "active",
            description = "El estado de activación que se asignará al Usuario.",
            example = "true")
        @PathVariable("active") boolean active) {
        setActiveUseCase.setActive(userId, active);
    }
}
