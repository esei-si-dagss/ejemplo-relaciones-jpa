package es.uvigo.esei.dagss.relaciones.entidades.bidireccional;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "BIDIRECCIONAL_PADRE")
public class BidireccionalPadre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String nombre;
    private Long edad;

    @ToString.Exclude  // Evita bucle en toString()
    @OneToMany(mappedBy = "padre", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)  
                   // orphanRemoval necesario para forzar en el em-merge()  borrados en BD al eliminar hijo de la lista del padre 
                   // cascade.REMOVE propaga borrado del padre a los hijos)
    private List<BidireccionalHijo> hijos;

}
