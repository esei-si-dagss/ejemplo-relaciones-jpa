package es.uvigo.esei.dagss.relaciones.entidades.bidireccional;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "BIDIRECCIONAL_HIJO")
public class BidireccionalHijo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    private String nombre;
    private Long edad;
    
    @ToString.Exclude  // Evita bucle en toString()
    @ManyToOne
    private BidireccionalPadre padre;
}
