[TOC]

### Ejemplos JPA

#### 0. Ejecución del ejemplo

Código fuente en (https://github.com/esei-si-dagss/ejemplo-relaciones-jpa)



El proyecto usa Maven como gestor de dependencias (ver `pom.xml`)

* Se usa la librería Lombook (https://projectlombok.org/) para autogenerar los constructores, _getter_ y _setter_ , etc, de las entidades.

* La implementación de JPA que se usa es la del proyecto Hibernate (https://hibernate.org/orm/) (ver `pom.xml` y `persistence.xml`)



El almacenamiento relacional se hace sobre la BD en memoria H2 (https://www.h2database.com/html/main.html) (ver `persistence.xml`)

* Esta BD ofrece una consola web para administrarla.
* En el ejemplo esta consola web se arranca desde el propio `main()` quedando disponible en la URL (http://localhost:10000)
* Desde esta consola web para conectar con la base de datos se debe indicar:
  * URL JDBC:  jdbc:h2:mem:test
  * Usuario: sa
  * Contraseña: <vacio>

```sh

git clone https://github.com/esei-si-dagss/ejemplo-relaciones-jpa
cd ejemplo-relaciones-jpa
mvn install
mvn exec:java -Dexec.mainClass=es.uvigo.esei.dagss.relaciones.Main

[detener la ejecución con Control+C]
```

**Nota:** en la configuración de Hibernate en `persistence.xml`se ha habilitado el log de las consultas SQL generadas, puede omitirse estableciendo la variable `hibernate.show_sql` a `false`



#### 1. Alternativas del mapeo de relaciones 1:N

Se incluyen diferentes ejemplos de creación de relaciones 1:N 

* Con mapeo bidireccional (paquete `java.es.uvigo.esei.dagss.relaciones.entidades.bidireccional`)

  * Se indica el atributo `mappedBy ` en el lado _One_ (implementa la relación 1:N con una clave foránea en el lado N) 
  * Puede probarse a omitir el `mappedBy` (se creará una tabla intermedia para implementar la relaicón 1:N)

* Sólo con mapeo desde el lado _One_ (paquete `java.es.uvigo.esei.dagss.relaciones.entidades.soloone`)
  * Indicando `@JoinColum` (implementa 1:N con clave foránea) 
  * Sin indicar ` @JoinColumn`(implementa 1:N usando una tabla intermedia)
* Sólo con mapeo desde el lado _Many_ (paquete `java.es.uvigo.esei.dagss.relaciones.entidades.solomany`) (implementa la relación 1:N con una clave foránea en el lado N) 

Puede comprobarse cómo son las tablas creadas desde la consola de H2.


#### 2. Creación de entidades y modificaciones en cascada (CascadeType.ALL)

Sobre las entidades mapeadas de forma bidireccional, la relación `OneToMany` está anotada con `CascadeType.ALL`.

* En el método ` crearEntidadesConCascadeALL()` de la clase `Main` se ilustra un `EntityManager.persist()` en cascada.
* En el método ` modificarConCascadeALL()` de la clase `Main` se ilustra un `EntityManager.merge()` en cascada. (requiere incluir `orphanRemoval=true` para realizar borrados)

#### 3. Acceso a relaciones FetchType.LAZY

Sobre las entidades mapeadas de forma bidireccional, la relación `OneToMany` está anotada con `FetchType.LAZY`.

* En el método `accesoLazzyDentroPersistenceContext()` de la clase `Main` se muestra cómo el acceso  a relaciones marcadas con _lazy_ funciona dentro del contexto de persistencia, pero no fuera. 
* Se comprueba que el acceso dentro del contexto de persistencia sí ''rellena'' la relación, mientras que el acceso fuera del contexto de persistencia lanza `LazyInitializationException`.
