package com.banco;

public class Cliente {

package com.banco;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

    @Entity
    public class Cliente extends PanacheEntity {

        @NotBlank
        public String nome;

        @NotBlank
        @Column(unique = true)
        public String cpf;

        @Email
        @NotBlank
        public String email;

        @NotBlank
        public String senha;
    }


}
