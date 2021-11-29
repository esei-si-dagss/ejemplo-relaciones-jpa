package es.uvigo.esei.dagss.relaciones.entidades.soloone;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EN_ONE_SINJOIN_PADRE")
@Setter @Getter
@NoArgsConstructor
public class EnOneSinJoinColumnPadre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    private String nombre;
    private Long edad;
    
    @OneToMany
    private List<EnOneSinJoinColumnHijo> hijos;

}
