# GestionParqueadero

## 0. Instrucciones de uso.

Clonar el repositorio en su máquina local: 
bash: 
"git clone https://github.com/JulianForeroBernal/GestionParqueadero.git"

O en su defecto hacer un pull para bajar las actualizaciones del repositorio:
"git pull origin main"

en proceso...

# Estructura del Proyecto

```text
GestionParqueadero/
│
├── database/
│   └── parqueadero.sql
│       └── Script SQL para crear la base de datos y las tablas necesarias.
│
├── docs/
│   ├── diagramas/
│   │   └── Archivos exportados de diagramas UML.
│   │
│   └── resources/
│       └── Archivos fuente de diagramas UML (.puml).
│
│       ├── Casos_de_uso.puml
│       │   └── Explica las interacciones entre los usuarios y el sistema.
│       │
│       ├── Diagrama_de_clases.puml
│       │   └── Muestra clases, atributos, métodos y relaciones.
│       │
│       └── Diagrama_de_secuencia.puml
│           └── Representa la secuencia de interacciones para el ingreso al parqueadero.
│
├── src/
│   └── main/
│       └── java/
│           │
│           ├── DAO/
│           │   └── Clases para conexión y consultas a la base de datos.
│           │
│           ├── enums/
│           │   └── Enumeraciones usadas en el sistema.
│           │
│           ├── model/
│           │   └── Entidades principales del sistema.
│           │
│           ├── util/
│           │   └── Clases utilitarias y validaciones.
│           │
│           └── Main.java
│
├── test/
│   └── Carpeta destinada a pruebas unitarias e integración.
│
├── .idea/
│   └── Configuración de IntelliJ IDEA.
│
├── .mvn/
│   └── Configuración de Maven.
│
├── .gitignore
│   └── Archivos ignorados por Git.
│
├── pom.xml
│   └── Configuración del proyecto Maven.
│
├── README.md
│   └── Documentación principal del proyecto.
│
└── docker-compose.yml
    └── Configuración de contenedores Docker.
```
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