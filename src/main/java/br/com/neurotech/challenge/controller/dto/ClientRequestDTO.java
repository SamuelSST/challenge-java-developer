package br.com.neurotech.challenge.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa o Cliente")
public class ClientRequestDTO {
    private String name;
    private Integer age;
    private Double income;
}

