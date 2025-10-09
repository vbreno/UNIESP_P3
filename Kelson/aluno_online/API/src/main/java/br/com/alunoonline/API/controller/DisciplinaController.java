package br.com.alunoonline.API.controller;

import br.com.alunoonline.API.model.Disciplina;
import br.com.alunoonline.API.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    DisciplinaService disciplinaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarDisciplina(@RequestBody Disciplina disciplina){
        disciplinaService.criarDisciplna(disciplina);
    }

    @GetMapping("professor/{professorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Disciplina> listarDisciplinaDoProf(@PathVariable Long professorId){
        return disciplinaService.listarDisciplinaDoProf(professorId);
    }

}
