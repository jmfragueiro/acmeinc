# acmeinc
Repositorio para evaluación de JAVA
===================================

# 👋 Acerca De

Es una pequeña aplicación Spring-boot REST basada en un esquema de proyecto maven multi-modulo para cumplir con una evaluación de conocimentos de JAVA con los requerimientos establecidos en el documento "tools/Ejercicio_JAVA-Especialista_Integracion.pdf" ubicado en la raíz del proyecto. Un diagrama general de la aplicación, incluyendo las clases y los aspectos más relevantes, se expone en el archivo "Diagrama General.pdf" en la raíz del proyecto.



## ⚙️ Requisitos

- Visual Studio Code: con extensiones:
  - Language Support for Java (Red Hat)
  - Markdown Preview Mermaid Support (Matt Bierner)
  - Maven for Java (Microsoft)
  - Debugger for Java (Microsoft)
  - REST Client (Huachao Mao)
  - Spring Boot Extension Pack (VMware)
- JDK 21
- Maven 3.8.4
- Squirrel-sql 4.8.0 Standard
- H2 Database JDBC Driver 2.3.232


## 📘 Módulos

El proyecto está compuesto por una estructura de módulos que combina ciertos conceptos de la arquitectura hexagonal, el clean code y mi experiencia en el desarrollo y mantenimiento de sistemas. Esto último impregna la implementación generando que ciertos conceptos de dichas teorias hayan sido adaptados a este enfoque que me ha servido, en la práctica, todos estos años. También he tenido en cuenta, en el diseño de la arquitectura, el tamaño de la aplicación requerida.

Los módulos son:

### Commons

Este módulo incluye definiciones y dependencias generales que se separan dado el bajo acoplamiento deseado entre el grupo de módulos model/application y el módulo bootstrap. De esta manera este módulo podría acompañar a cualquiera de los demás en una portación para reutilización en otro proyecto y sin necesidad de agregar toda la cadena de dependencias.

### Model

Es el módulo que contiene las definiciones del modelo de datos y de los repositorios asociados. La organización del código no está basada en una estructura estándar de directorios sino en un esquema de nomenclatura y un agrupamiento por lo que denomino 'concepto de negocio'. Se define un paquete "base" que define una plantilla general para entidades y repositorios unificando, apalancado en clases genéricas, el código común y permitiendo así imponer ciertas políticas globales y minimizar el código a escribir/mantener. Se coloca aquí el paquete "base/principal" que tiene definiciones que sirven de base para las aplicadas luego en el módulo "adapter" para desacoplar dichos grupos de módulos.

### Application

Contiene las clases del modelo de negocio y los controladores asociados. Aqui también vale lo dicho anteriromente respecto de la organización del código. Se define un paquete "template" que define una plantilla para los controladores CRUD y los servicios básicos unificando, apalancado en clases genéricas, el código común y permitiendo así imponer ciertas políticas globales y minimizar el código a escribir/mantener. Se coloca aquí el paquete "template/principal" que tiene definiciones que sirven de base para las aplicadas luego en el módulo "adapter" para desacoplar dichos grupos de módulos.

### Adapter

Aqui se utiliza el nombre únicamente para dar a entender que es un módulo de interfase entre la "aplicación" (definida en los módulos Model y Application) y el "bootstrap" (definido en el módulo Bootstrap), de manera que ambos grupos sean casi totalmente independientes, a partir de un acoplaiento mínimo basado únicamente en la interacción 'sesión <- principal -> usuario', la cual igualmente, gracias a este módulo, no traspasa las fronteras ni genera dependencias mas fuertes que las mínimas necesarias.

### Bootstrap

Es el módulo encargado de proveer de los servicios de inicialización, gestión de sesiones, control de autenticaciones y autorizaciones. Es el módulo que más depende de, y más trbaja con, las caractarísticas específicas de SpringBoot y justamente su separación permite que la dependencia de los demás módulos para con este framework se reduzca solamente a anotaciones estándar, que se encuentran aplicadas de manera igual o muy similar en otros frameworks. De esta forma, dado el bajo acoplamiento alcanzado para con los módulos Model/Application, se consigue una dependencia suficientemente baja de la aplicación para con SpringBoot y, de igual manera, del bootstrap (con todos sus servicios asociados comentados) para con la aplicación, permitiendo una portación y reutilización relativamente sencilla de cualquiera de los dos grupos.
Este módulo además presenta una pequeña capa, bien simple y específica, de manejo de authenticación, autorización y manejo de sesiones, de manera de minimzar la arquitectura utilizada para este tipo de funciones (Spring es un framework muy generico y global a expensas de tamaño y tiempo de inicio/ejecución), reemplanzado, en la medida lo posible, la estructura de Spring Security, pero manteniendo la compatibilidad de forma de que se puedan utilizar otras partes del framework dque dependen de los conceptos centrales de esta (como Authentication o GrantedAuthority). De igual forma esta capa permitiría acoplar un mecanismo externo de autenticación/autorizacion como keycloack o similar.


## 🚀 Cómo ejecutar las pruebas

La forma mas sencilla es ejecutarlo directamente desde el Visual Studio Code (con la configuración de Launch "SPRINGBOOT").

O puede ejecutarse 'a mano' yendo al directorio raíz del proyecto y:

- 1) ejecutar el siguiente comando para compilar, probar, empaquetar e instalar los diferentes artefactos en tu repositorio local de maven:

```shell
mvn clean install
```

O utilizar el wrapper de maven integrado al proyecto, si no se tiene una instalación de maven propia, con:

```shell
./mvnw clean install
```

- 2) Hay que setear las siguientes variables de entorno (si se ejecuta desde VSCode ya están configuiradas):

  - "SPRING_CONFIG_LOCATION": "classpath:/application.properties,file:${workspaceFolder}\\local\\appext.properties",
  - "LOGGING_HOME":"${workspaceFolder}\\local\\logs"

- 3) Después de crear todos los artefactos, ejecutar el proyecto con el siguiente comando:

```shell
mvn spring-boot:run -pl bootloader
```

O utilizar el wrapper de maven integrado al proyecto, si no se tiene una instalación de maven propia, con:

```shell
./mvnw spring-boot:run -pl bootloader
```

Si la aplicación queda funcionando correctamente se puede ejecutar las pruebas con abriendo, desde el Visual Studio Code, el archivo tools/postman.http (el cual se ejecuta gracias a la extensión "REST Client"). Ese archivo tiene una serie de pruebas en orden y explica como pasar los valores necesarios entre cada prueba.

Por otro lado queda disponible el endpoint:

- `http://localhost:8081/swagger-ui.html`: Interfaz Swagger basada en el esquema autogenerado de OpenAPI. NOTA: Los UseCases AddPhone y SetActive están configurados con valores especificos.

Vale aclarar finalmente que los logs se generan en el directorio definido por la variable de entorno: LOGGING_HOME.