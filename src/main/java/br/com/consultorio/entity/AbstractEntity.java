package br.com.consultorio.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractEntity {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @Column(name = "cadastro", nullable = false)
    private LocalDateTime cadastro;

    @Getter
    @Setter
    @Column(name = "atualizado")
    private LocalDateTime atualizado;

    @Getter
    @Setter
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

     @PrePersist
     public void atualizarDataCadastro() {
         this.cadastro = LocalDateTime.now();
     }
      @PreUpdate
         public void atualizarDataAtualizar() {
          this.atualizado = LocalDateTime.now();
      }

}
