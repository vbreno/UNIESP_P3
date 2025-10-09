package br.com.alunoonline.API.dtos;

import lombok.Data;

//Transfere somente os dados necessários
@Data
public class AtualizarNotasRequestDTO {
    private Double nota1;
    private Double nota2;
}
