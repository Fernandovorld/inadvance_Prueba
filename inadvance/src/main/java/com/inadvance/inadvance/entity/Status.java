package com.inadvance.inadvance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Status  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "UTC")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date fechaUltimoIngreso;

    private String token;

    private String name;

    // Nuevo campo para registrar la actividad realizada
    private String actividad;
}
