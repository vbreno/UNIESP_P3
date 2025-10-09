package br.com.alunoonline.API.service;

import br.com.alunoonline.API.dtos.AtualizarNotasRequestDTO;
import br.com.alunoonline.API.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.API.model.MatriculaAluno;
import br.com.alunoonline.API.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MatriculaAlunoService {

    private static final Double MEDIA_PARA_APROVACAO = 7.0;
    private static final Integer QTD_NOTAS = 2;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;
    public void criarMatricula(MatriculaAluno matriculaAluno){
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }
    public void trancarMatricula(Long matriculaAlunoId){
        // Verifica se a matrícula existe
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.findById(matriculaAlunoId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula Aluno não encontrada."));

        // Só irá trancar se o status atual for matriculado
        if (matriculaAluno.getStatus().equals(MatriculaAlunoStatusEnum.MATRICULADO)){
            matriculaAluno.setStatus(MatriculaAlunoStatusEnum.TRANCADO);
            matriculaAlunoRepository.save(matriculaAluno);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar com status MATRICULADO");
        }
    }
    public void atualizarNotas(Long matriculaAlunoId, AtualizarNotasRequestDTO atualizarNotasRequestDTO){
        MatriculaAluno matriculaAluno = buscarMatriculaOuLancarExcecao(matriculaAlunoId);
        if (atualizarNotasRequestDTO.getNota1() != null){
            matriculaAluno.setNota1(atualizarNotasRequestDTO.getNota1());
        }
        if (atualizarNotasRequestDTO.getNota2() != null){
            matriculaAluno.setNota2(atualizarNotasRequestDTO.getNota2());
        }
        calcularMediaEModificarStatus(matriculaAluno);
        matriculaAlunoRepository.save(matriculaAluno);

    }
    private MatriculaAluno buscarMatriculaOuLancarExcecao(Long matriculaAlunoId){
        return matriculaAlunoRepository.findById(matriculaAlunoId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Matricula do aluno não encontrada"));
    }

    private void calcularMediaEModificarStatus(MatriculaAluno matriculaAluno){
        Double nota1 = matriculaAluno.getNota1();
        Double nota2 = matriculaAluno.getNota2();
        if (nota1 != null && nota2 != null){
            Double media = (nota1+nota2)/QTD_NOTAS;
            matriculaAluno.setStatus(media >= MEDIA_PARA_APROVACAO ? MatriculaAlunoStatusEnum.APROVADO : MatriculaAlunoStatusEnum.REPROVADO);
        }
    }
}