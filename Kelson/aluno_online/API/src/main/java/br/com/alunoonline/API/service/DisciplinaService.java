package br.com.alunoonline.API.service;

import br.com.alunoonline.API.model.Disciplina;
import br.com.alunoonline.API.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository disciplinaRepository;

    public void criarDisciplna(Disciplina disciplina){
        disciplinaRepository.save(disciplina);
    }
    public List<Disciplina> listarDisciplinaDoProf(Long professorId){
        return disciplinaRepository.findByProfessorId(professorId);
    }
}
