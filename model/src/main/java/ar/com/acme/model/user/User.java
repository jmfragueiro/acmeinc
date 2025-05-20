package ar.com.acme.model.user;

import ar.com.acme.model.phone.Phone;
import ar.com.acme.commons.Constants;
import ar.com.acme.commons.Logging;
import ar.com.acme.commons.principal.IPrincipalUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tab_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends ar.com.acme.model.base.entity.Entity implements IPrincipalUser<UUID> {
    public static final String ENTITY_NAME = "User";
    public static final String FIELD_NAME = "Name";
    public static final String FIELD_EMAIL = "Email";
    public static final String FIELD_PASSWORD = "Password";
    public static final String FIELD_ACTIVE = "Active";
    public static final String ERR_NOT_ENABLED = "EL USUARIO NO SE ENCUENTRA ACTIVO";
    public static final String ERR_BAD_EMAIL = "LA DIRECCION DE EMAIL INGRESADA NO TIENE FORMATO VALIDO";
    public static final String ERR_REPET_EMAIL = "EL CORREO YA SE ENCUENTRA REGISTRADO";
    public static final String ERR_BAD_PASSWORD = "EL PASSWORD INGRESADO NO TIENE FORMATO VALIDO";

    @Column(name = "name", unique = true)
    @NotNull(message = Constants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_NAME)
    @Size(min = 4, max = 16, message = Constants.MSJ_REP_ERR_FIELD_LONG_NOK + FIELD_NAME)
    private String name;

    @Column(name = "email", unique = true)
    @NotNull(message = Constants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_EMAIL)
    private String email;

    ///////////////////////////////////////////////////////
    // PARA GENERAR LAS CLAVES INICIALES PARA PROBAR:    //
    // (hay que debuggear y parar en la captura de pass  //
    //  y guardar el pass generado en la base de datos)  //
    ///////////////////////////////////////////////////////
    // var passService = new PaswordService();           //
    // var pass = passService.encode("nuevo_password");  //
    ///////////////////////////////////////////////////////
    @Column(name = "password")
    @NotNull(message = Constants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_PASSWORD)
    private String password;

    @Column(name = "active")
    @NotNull(message = Constants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_ACTIVE)
    private Boolean active;

    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;

    @Column(name = "token")
    private UUID token;

    @Valid
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Phone> phones = new HashSet<>();

    @Override
    public boolean canOperate() {
        if (!isAlive()) {
            Logging.info(getClass(), Constants.MSJ_USR_ERR_DELETED);
            return false;
        }

        if (!getActive()) {
            Logging.info(getClass(), Constants.MSJ_USR_ERR_USERINACTIVE);
            return false;
        }

        return true;
    }
}
