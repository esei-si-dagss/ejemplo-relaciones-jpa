package es.uvigo.esei.dagss.relaciones.entidades.solomany;

import es.uvigo.esei.dagss.relaciones.entidades.bidireccional.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "EN_MANY_HIJO")
public class EnManyHijo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    private String nombre;
    private Long edad;
    
    @ManyToOne
    private EnManyPadre padre;
}
