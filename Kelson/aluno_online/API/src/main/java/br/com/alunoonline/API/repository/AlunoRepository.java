package br.com.alunoonline.API.repository;

import br.com.alunoonline.API.model.Aluno;
import org.hibernate.sql.Delete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
