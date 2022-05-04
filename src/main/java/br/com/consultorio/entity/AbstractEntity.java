package br.com.consultorio.entity;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.time.LocalDateTime;
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "cadastro", nullable = false)
    private LocalDateTime cadastro;
    @Column(name = "atualizado", nullable = false)
    private LocalDateTime atualizado;
    @Column(name = "excluido", nullable = false)
    private LocalDateTime excluido;

     @PrePersist
     public void atualizarDataCadastro() {
         this.cadastro = LocalDateTime.now();
     }
      @PreUpdate
         public void atualizarDataAtualizar() {
          this.atualizado = LocalDateTime.now();
      }

}
