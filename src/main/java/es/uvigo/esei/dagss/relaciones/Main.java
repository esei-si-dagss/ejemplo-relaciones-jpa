package es.uvigo.esei.dagss.relaciones;

import es.uvigo.esei.dagss.relaciones.entidades.bidireccional.BidireccionalHijo;
import es.uvigo.esei.dagss.relaciones.entidades.bidireccional.BidireccionalPadre;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.h2.tools.Server;

public class Main {

    private static EntityManagerFactory emf;

    public static final void main(String[] args) throws SQLException {
        emf = Persistence.createEntityManagerFactory("relaciones_PU");

        crearEntidadesConCascadeALL();
        accesoLazzyDentroPersistenceContext();
        modificacionConCascadeALL();
        crearConsolaWeb();

        emf.close();
        
        // Ejecución con maven: 
        // mvn install 
        // mvn exec:java -Dexec.mainClass=es.uvigo.esei.dagss.relaciones.Main
        //
        // Consola web de H2
        // http://localhost:10000
        // URL JDBC:  jdbc:h2:mem:test
        // Usuario: sa
        // Contraseña: <vacio>
    }

    private static void crearEntidadesConCascadeALL() {
        DUMP("CREAR ENTIDADES CON CASCADE ALL");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {

            tx.begin();

            BidireccionalPadre padre1 = BidireccionalPadre.builder().nombre("padre1").edad(40l).hijos(new ArrayList<>()).build();
            padre1.getHijos().add(BidireccionalHijo.builder().nombre("hijo1de1").edad(10l).padre(padre1).build());
            padre1.getHijos().add(BidireccionalHijo.builder().nombre("hijo2de1").edad(10l).padre(padre1).build());
            padre1.getHijos().add(BidireccionalHijo.builder().nombre("hijo3de1").edad(10l).padre(padre1).build());

            em.persist(padre1); // Inserta también a los hijos (Cascade.ALL)

            BidireccionalPadre padre2 = BidireccionalPadre.builder().nombre("padre2").edad(40l).hijos(new ArrayList<>()).build();
            padre2.getHijos().add(BidireccionalHijo.builder().nombre("hijo1de2").edad(10l).padre(padre2).build());
            padre2.getHijos().add(BidireccionalHijo.builder().nombre("hijo2de2").edad(10l).padre(padre2).build());

            em.persist(padre2); // Inserta también a los hijos (Cascade.PERSIST)

            tx.commit();
        } catch (Exception e) {
            DUMP(" > Error en crearConCascadeALL");
            e.printStackTrace(System.err);

            if ((tx != null) && tx.isActive()) {
                tx.rollback();
            }
        }
        em.close();
        DUMP("");
    }

    private static void accesoLazzyDentroPersistenceContext() {
        DUMP("ACCESO LAZY DENTRO DE PERSISTENCE CONTEXT");

        BidireccionalPadre padre1 = null;
        BidireccionalPadre padre2 = null;

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            padre1 = em.find(BidireccionalPadre.class, 1L);
            padre2 = em.find(BidireccionalPadre.class, 2L);

            DUMP("> Padre 1: " + padre1);
            DUMP("> Hijos de padre1 (dentro de contexto): " + padre1.getHijos());

            tx.commit();
        } catch (Exception e) {
            DUMP(" > Error en accesoLazzy");
            e.printStackTrace(System.err);

            if ((tx != null) && tx.isActive()) {
                tx.rollback();
            }
        }
        em.close();

        DUMP("> Padre 2: " + padre2);
        try {
            DUMP("> Hijos de padre2 (fuera del contexto): " + padre2.getHijos());
        } catch (Exception e) {
            DUMP("> Hijos de padre2 (fuera del contexto): Error en acceso a hijos");
            DUMP("> Excepcion: " + e.toString());
        }

        DUMP("");
    }

    private static void modificacionConCascadeALL() {
        DUMP("MODIFICAR ENTIDADES CON CASCADE ALL");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            BidireccionalPadre padre1 = em.find(BidireccionalPadre.class, 1L);

            DUMP("> Padre 1 (inicial): " + padre1);
            DUMP("> Hijos de padre1 (inicial): " + padre1.getHijos());

            // modificar padre y primer hijo
            padre1.setNombre("padre1 (modificado)");
            padre1.getHijos().get(0).setNombre("hijo1de1 (modificado)");
            // Borra ultimo hijo
            padre1.getHijos().remove(2);

            padre1.getHijos().add(BidireccionalHijo.builder().nombre("hijo4de1 (nuevo)").edad(5l).padre(padre1).build());

            BidireccionalPadre padre1Modificado = em.merge(padre1); // Actualiza también a los hijos (Cascade.MERGE)
            // orphanRemoval=true hace que se borren en la BD los hijos eliminados de la lista  

            DUMP("> Padre 1 (modificado): " + padre1Modificado);
            DUMP("> Hijos de padre1 (modificado): " + padre1Modificado.getHijos());

            tx.commit();
        } catch (Exception e) {
            DUMP(" > Error en modificacionConCascadeALL");
            e.printStackTrace(System.err);

            if ((tx != null) && tx.isActive()) {
                tx.rollback();
            }
        }
        em.close();
        DUMP("");
    }

    private static void crearConsolaWeb() throws SQLException {
        // start the H2 DB TCP Server
        Server server = Server.createWebServer("-webPort", "10000", "-web", "-webAllowOthers").start();
        // stop the H2 DB TCP Server

        Scanner input = new Scanner(System.in);
        System.out.print("Pulsar Enter para terminar...");
        input.nextLine();
        server.stop();

    }

    private static void DUMP(String s) {
        System.out.println(":::::::::: " + s);
    }

}
