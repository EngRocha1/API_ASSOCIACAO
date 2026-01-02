package com.example.work3.v1.SuperClasses.classes;
import jakarta.persistence.*;
import lombok.*;

/**
 * Superclasse abstrata que contém campos comuns para entidades de listagem.
 * Herda os seguintes campos da classe Auditable:
 * - id: Identificador único da entidade.
 * - name: Nome da entidade.
 * - createdDate: Data de criação da entidade.
 * - modifiedDate: Data da última modificação da entidade.
 * - createdBy: Usuário que criou a entidade.
 * - modifiedBy: Usuário que modificou a entidade.
 *
 * Adiciona os seguintes campos:
 * - descricao: Descrição da entidade.
 * - ativo: Indica se a entidade está ativa.
 */

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Listagem extends Auditable {

    private String name;
    private String descricao;
    private boolean ativo;
}
