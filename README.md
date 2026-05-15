# GestionParqueadero

## 0. Instrucciones de uso.

Clonar el repositorio en su máquina local: 
bash: 
"git clone https://github.com/JulianForeroBernal/GestionParqueadero.git"

O en su defecto hacer un pull para bajar las actualizaciones del repositorio:
"git pull origin main"

en proceso...

## 1. Estructura del proyecto.

GestionParqueadero/
│
├── database/
│   └── parqueadero.sql (archivo de script SQL para crear la base de datos y tablas necesarias)
│
├── docs/
│   ├── diagramas/ (archivos con diagramas de diseño del sistema, como diagramas de clases, diagramas casos de uso, etc.)
│   └── resources/ (archivos con recursos adicionales para el proyecto, principalmente archivos .uml para los diagramas de diseño)
│       └──Casos_de_uso.puml (explica la interacciones entre el usuario y el sistema, y las funcionalidades que el sistema debe ofrecer)
|       └──Diagrama_de_clases.puml (muestra las clases del sistema, sus atributos, métodos y relaciones entre ellas)
|       └──Diagrama_de_secuencia.puml (muestra la secuencia de interacciones entre clases para realizar una funcionalidad específica del sistema, en este caso la mas importante: el ingreso al parqueadero)
└── src/ (carpeta con el código fuente del proyecto)
|   └── main/
│       └── java/
|           └── DAO/ (carpe con clases para la gestión de la base de datos, como conexión, consultas, etc.)
│           ├── enums/ (carpeta con enumeraciones usadas en el proyecto, como tipos de vehículos, etc.)
│           ├── model/ (carpeta con clases que representan las entidades del sistema, como Usuario, Vehiculo, etc.)
|           ├── util/  (carpeta con clases de utilidad, como generación de códigos de barras, validaciones, conexión con la base de datos etc.)
|           |
│           ├──Main.java
│
│
└── test/ (carpa con el código de pruebas unitarias y de integración no usado en este proyecto por ahora)
│
├──.ida/ (carpe con configuraciones de IntelliJ IDEA, puede ser ignorada si no se usa este IDE)
├── .mvn/ (carpeta con archivos de configuración de Maven, puede ser ignorada si no se usa Maven)
├── .gitignore (archivo para ignorar archivos y carpetas en el control de versiones)
├── pom.xml (archivo de configuración de Maven, puede ser ignorado si no se usa Maven)
├── README.md (archivo de documentación del proyecto)
├── docker-compose.yml (archivo de configuración para Docker, puede ser ignorado si no se usa Docker)


### 1.1 Info importante para el profesor:
Como parte de nuestra propia investigación para el desarrollo del proyecto implementamos ciertos cambios pequeños a lo aprendido en clase, cambios los cuales serán explicados en esta sección
- utilizamos una carpeta llamada "enums" para almacenar las enumeraciones usadas en el proyecto, es decir los tipos de vehículos usados. esto con el fin de mantener una mejor organización del código y facilitar su mantenimiento.
- en lugar de hacer un DAO para cada entidad del sistema, decidimos implementar una sola interfaz "CRUDL<T>" general para realizar las operaciones básicas de creación, lectura, actualización, eliminación y listado de cualquier entidad del sistema, esto con el fin de reducir la cantidad de código repetitivo y mejorar la eficiencia en el desarrollo.
  - cabe resaltar que la "etiqueta" <T> es un tipo genérico lo que significa que puede utilizar cualquier tipo de objeto.
- existe un nuevo archivo llamado docker-compose.yml el cual contiene la configuración necesaria para ejecutar el sistema en un contenedor Docker, esto con el fin de facilitar su despliegue en diferentes entornos sin necesidad de configurar manualmente la base de datos y el entorno de ejecución. (explicado con mayor detalle en la sección de Instrucciones de uso)

## 2. Descripción del proyecto
El proyecto consiste en desarrollar un sistema para la gestión del parqueadero universitario mediante el uso de códigos de barras únicos para cada estudiante.
Actualmente, el ingreso al parqueadero se realiza de forma manual mediante papelillos entregados por orden de llegada, lo que genera problemas varios al ingreso de la universidad como demoras, falta de claridad y seguridad, entre otros.

El sistema permitirá automatizar el control de ingreso y salida de vehículos, registrar ocupación de espacios y consultar disponibilidad en tiempo real tanto para parqueadero de carros, motos, y bicicletas.

## 3. Descripción de problema
Bajo el sistema actual de gestion de los parqueaderos, se presentan los siguientes problemas o dificultades:
- Uso de papelillos fáciles de perder.
- Verificación manual del carnet estudiantil (validadcion como integrante de la universidad).
- Demoras en el ingreso al parqueadero.
- No existe control automatizado de cupos (lo genera confusion acerca de la realidad de espacios disponibles).
- No existe historial digital de ingresos y salidas.

Como se puede apreciar, el sistema actual es ineficiente, anticuado y propenso a errores humanos, lo que afecta la experiencia de los estudiantes y la seguridad del parqueadero.

## 4. Objetivo general.
Desarrollar un sistema de gestión de parqueaderos universitarios que permita controlar el ingreso y salida de los distintos vehículos usados por la comunidad universitaria mediante códigos de barras, mostrando en tiempo real la disponibilidad de espacios.

## 5. Objetivos específicos.
- Registrar usuarios y sus vehículos asociados en una base de datos.
- Generar códigos de barras únicos para cada usuario registrado.
- mostrar en tiempo real la disponibilidad de espacios en el parqueadero.
- Permitir el ingreso y salida de vehículos mediante la lectura de códigos de barras.
- Mantener un historial digital de ingresos y salidas para fines de control y seguridad.

## 6. Requerimientos funcionales.
- Registrar usuarios (puede ser estudiante, profesor, o personal de la U indistintamente)
- Registrar vehículos asociados a cada usuario (carro, moto, bicicleta).
- Generar un código único para cada usuario y vehículo del usuario (es decir cada vehículo tiene un código, y cada usuario puede tener más de un vehículo).
- Permitir el ingreso mediante escaneo del código (por ahora al digitarlo más adelante al escanearlo).
- Registrar entradas y salidas automáticamente.
- Mostrar la cantidad de espacios disponibles.

## 7. Requerimientos no funcionales.
- El sistema debe ser fácil de usar e intuitivo para los usuarios.
- El sistema debe ser seguro y proteger la información de los usuarios.
- El sistema debe responder rápidamente.
- El sistema debe permitir futuras expansiones web o móvil.

## 8. Tecnologías utilizadas.
- Java: para el desarrollo del backend y la lógica del sistema.
- MySQL: para la gestión de la base de datos.
- JavaFX (librería): para el desarrollo de la interfaz gráfica de usuario (GUI).
- Librería de códigos de barras (como ZXing): para generar y leer códigos de barras.
- Git: para el control de versiones y colaboración en el desarrollo del proyecto.
- Maven: para la gestión de dependencias y construcción del proyecto.
- Docker: para la contenedorización del sistema y facilitar su despliegue en diferentes entornos.


## 10.Metodología de desarrollo.
Se utilizará una metodología de desarrollo incremental, realizando avances pequeños y funcionales en cada etapa del proyecto. El trabajo se dividirá entre diseño, desarrollo y pruebas para facilitar la organización del equipo
donde cada miembro del equipo se encargará de tareas específicas, como el desarrollo del backend, la creación de la interfaz gráfica, la gestión de la base de datos y la integración de la funcionalidad de códigos de barras, etc. con apoyo mutuo en caso de dificultades, independientemente de su rol específico o tarea asignada.

## 11 Alcance del proyecto.
El proyecto busca desarrollar un prototipo funcional para la gestión de parqueaderos universitarios, inicialmente ejecutable en entorno local mediante Java y MySQL.

## 12 Futuras mejoras.
- Implementación de una aplicación móvil para facilitar el acceso y uso del sistema.
- Implementación de un sistema de notificaciones para informar a los usuarios sobre la disponibilidad de espacios
- Mejoras en la interfaz gráfica para una experiencia de usuario más atractiva e intuitiva.
- Mejoras en la ciberseguridad para proteger la información de los usuarios y prevenir accesos no autorizados.
- Integración con scanners y/o cámaras para automatizar completamente el proceso de ingreso y salida sin necesidad de interacción manual.
- Implementación en la página web de la universidad.
- Generación de estadísticas.
- -implementacion de más funciones orientadas a un administrador, como consultar registros, bloquear usuarios, etc.