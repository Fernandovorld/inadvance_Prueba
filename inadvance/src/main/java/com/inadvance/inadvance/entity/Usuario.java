package com.inadvance.inadvance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Email(message = "El formato del correo electrónico no es válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "UTC")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date fechaCreacion;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "UTC")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date fechaModificacion;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario")
    private List<Phones> phones;




}
