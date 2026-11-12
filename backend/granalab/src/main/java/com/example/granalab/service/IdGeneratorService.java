package com.example.granalab.service;

import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Serviço de geração de identificadores UUID.
 * O banco de dados não utiliza AUTO_INCREMENT, portanto os identificadores
 * (id_user, id_aluno, id_mentor) são gerados pela aplicação.
 * O mesmo UUID gerado para o usuário é reaproveitado como id_aluno ou id_mentor,
 * conforme o tipo de perfil escolhido no cadastro.
 */
@Service
public class IdGeneratorService {

    /**
     * Gera um novo UUID para ser utilizado como identificador de usuário
     * e, consequentemente, como identificador do perfil associado (aluno ou mentor).
     *
     * @return UUID gerado
     */
    public UUID gerarId() {
        return UUID.randomUUID();
    }
}