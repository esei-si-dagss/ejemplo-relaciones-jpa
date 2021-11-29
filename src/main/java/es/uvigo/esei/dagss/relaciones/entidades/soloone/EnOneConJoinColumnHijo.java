package es.uvigo.esei.dagss.relaciones.entidades.soloone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "EN_ONE_CONJOIN_HIJO")
public class EnOneConJoinColumnHijo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    private String nombre;
    private Long edad;
    
}
