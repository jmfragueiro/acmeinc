package ar.com.acme.application.user.usecases.addphone;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.com.acme.application.phone.mappers.PhoneInMapper;
import ar.com.acme.application.phone.mappers.PhoneOutMapper;
import ar.com.acme.commons.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Constants.URL_CONTROLLER_USER)
@RequiredArgsConstructor
public class UserAddPhoneController {
    private final IUserAddPhoneUseCase addPhoneUseCase;

    @Operation(summary = "Agregar Telefono a Usuario",
               description = "Permite agregar un telefono a un usuario. Si el teléfono no existe, entonces lo crea.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Teléfono agregado al Usuario"),
        @ApiResponse(responseCode = "401", description = "El cliente no tiene permisos suficiente para la operación.")})
    @PostMapping("/{userId}/phones")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneOutMapper addPhone(
        @Parameter(
            name = "userId",
            description = "ID del Usuario al que se agregará el teléfono",
            example = "1a245fd4-35f4-40d3-b5b4-3d4d30a66e02")
        @PathVariable("userId") UUID userId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "El teléfono a crear y agregar al usuario",
            required = true,
            content = @Content(mediaType = "application/json",
                               schema = @Schema(implementation = PhoneInMapper.class),
                               examples = @ExampleObject(value = "{\"number\":\"123456\",\"citycode\":\"376\",\"countrycode\":\"54\"}")))
        @RequestBody PhoneInMapper wiPhone) {
        return PhoneOutMapper.fromPhone(addPhoneUseCase.addPhone(userId, wiPhone));
    }
}
