# Gestión de Seguros

Aplicación de escritorio para gestionar pólizas de seguro: crear, consultar, actualizar y eliminar (CRUD) en una base de datos MySQL. La interfaz es JavaFX y el código sigue una **arquitectura hexagonal** (puertos y adaptadores).

> El CRUD aplica solo sobre **seguros**. Los **asegurados** son datos predeterminados que se cargan con el script SQL y la aplicación solo los consulta.

## Tecnologías

| Componente | Versión |
|---|---|
| Java (JDK) | 21 |
| JavaFX | 21.0.5 |
| MySQL Server | 8.x |
| MySQL Connector/J | 8.4.0 |
| Maven | 3.9+ |

## Modelo de dominio

| Entidad | Atributos |
|---|---|
| `Seguro` (abstracta) | número, fecha de expedición, valor, estado, asegurado |
| `SeguroVida` (hereda de `Seguro`) | nombre del beneficiario, parentesco |
| `SeguroVehiculo` (hereda de `Seguro`) | marca |
| `Asegurado` | identificación, nombre, correo electrónico |

Un asegurado puede tener varios seguros (asociación `Seguro 0..* → 1 Asegurado`).

**Reglas de validación** (`Seguro.validar()` y sus redefiniciones):

- Número, estado y asegurado son obligatorios.
- La fecha de expedición es obligatoria y no puede ser futura.
- El valor debe ser mayor que cero.
- Seguro de vida: beneficiario y parentesco son obligatorios.
- Seguro de vehículo: la marca es obligatoria.
- No puede haber dos seguros con el mismo número (lo verifica `CrearSeguroServicio`).

## Arquitectura

```
          Usuario
             │
   ┌─────────▼─────────┐      ┌──────────────── Hexágono ────────────────┐      ┌────────────────────────┐
   │ Adaptador entrada │      │ puerto.entrada → servicio → puerto.salida │      │ Adaptador salida       │
   │ infraestructura/ui│─usa─▶│                    │                     │◀─────│ infraestructura/       │
   │ MainController    │      │                    ▼                     │ impl │   persistencia         │
   │ GestorSeguros.fxml│      │             dominio/modelo               │      │ MySql*Repository       │
   └───────────────────┘      └──────────────────────────────────────────┘      │ ConexionBD ──JDBC──▶ MySQL
                                                                                └────────────────────────┘
                     vista/App.java: raíz de composición (crea y conecta todas las piezas)
```

Las dependencias siempre apuntan hacia el centro:

- El **dominio** solo usa `java.*`; no conoce JavaFX ni JDBC.
- Los **servicios** dependen de interfaces (puertos de salida), nunca de MySQL.
- El **controlador** depende solo de los puertos de entrada (casos de uso).
- `App` es el único lugar que crea objetos concretos e inyecta las dependencias por constructor: repositorios → servicios → controlador.

### Puertos y adaptadores

| Caso de uso (puerto de entrada) | Servicio | Puertos de salida usados |
|---|---|---|
| `CrearSeguroUseCase` | `CrearSeguroServicio` | `GuardarSeguroPort`, `BuscarSeguroPort` |
| `ConsultarSeguroUseCase` | `ConsultarSeguroServicio` | `BuscarSeguroPort` |
| `ConsultarSegurosUseCase` | `ConsultarSegurosServicio` | `BuscarSegurosPort` |
| `ActualizarSeguroUseCase` | `ActualizarSeguroServicio` | `ActualizarSeguroPort` |
| `EliminarSeguroUseCase` | `EliminarSeguroServicio` | `EliminarSeguroPort` |
| `ConsultarAseguradosUseCase` | `ConsultarAseguradosServicio` | `BuscarAseguradosPort` |

| Adaptador de salida | Implementa |
|---|---|
| `MySqlSeguroRepository` | `GuardarSeguroPort`, `BuscarSeguroPort`, `BuscarSegurosPort`, `ActualizarSeguroPort`, `EliminarSeguroPort` |
| `MySqlAseguradoRepository` | `BuscarAseguradosPort` |
| `ConexionBD` | Singleton que entrega la conexión JDBC a los repositorios |

**Manejo de errores:** las reglas de negocio lanzan `IllegalArgumentException`; los fallos técnicos de base de datos se envuelven en `PersistenciaException` (unchecked, definida junto a los puertos de salida). El controlador captura ambas y las muestra en un diálogo.

## Estructura del proyecto

```
Parcial2/
├── bd/
│   └── gestion_seguros.sql                 # creación de la BD y datos iniciales
├── pom.xml
└── src/main/
    ├── java/co/edu/poli/sw2/
    │   ├── dominio/modelo/                 # Seguro, SeguroVida, SeguroVehiculo, Asegurado
    │   ├── aplicacion/
    │   │   ├── puerto/entrada/             # 6 interfaces *UseCase
    │   │   ├── puerto/salida/              # 6 interfaces *Port + PersistenciaException
    │   │   └── servicio/                   # 6 implementaciones *Servicio
    │   ├── infraestructura/
    │   │   ├── ui/                         # MainController (adaptador de entrada)
    │   │   └── persistencia/               # MySql*Repository, ConexionBD (adaptadores de salida)
    │   └── vista/App.java                  # punto de entrada y raíz de composición
    └── resources/
        ├── db.properties                   # URL, usuario y contraseña de MySQL
        └── co/edu/poli/sw2/infraestructura/ui/GestorSeguros.fxml
```

## Base de datos

Esquema `gestion_seguros` con dos tablas:

- `asegurado(identificacion PK, nombre, correo_electronico)`
- `seguro(numero PK, fecha_expedicion, valor, estado, tipo, id_asegurado FK, nombre_beneficiario, parentesco, marca)`

La herencia se guarda en **una sola tabla**: la columna `tipo` (`VIDA` o `VEHICULO`) indica la subclase, y las columnas de la otra subclase quedan en `NULL`.

El script carga 4 asegurados y 3 seguros de ejemplo.

## Instalación y ejecución

1. **Crear la base de datos.** El script borra y vuelve a crear `gestion_seguros`:

   ```bash
   mysql -u root -p < bd/gestion_seguros.sql
   ```

   También puede abrirse y ejecutarse desde MySQL Workbench.

2. **Configurar la conexión** en `src/main/resources/db.properties`:

   ```properties
   db.url=jdbc:mysql://localhost:3306/gestion_seguros?serverTimezone=America/Bogota
   db.usuario=root
   db.contrasena=root
   ```

3. **Ejecutar la aplicación:**

   ```bash
   mvn clean javafx:run
   ```

## Uso

| Botón | Acción |
|---|---|
| **Crear** | Registra el seguro con los datos del formulario. |
| **Consultar** | Busca por el número escrito y lo muestra en la tabla y el formulario. |
| **Actualizar** | Guarda los cambios del seguro identificado por su número. |
| **Eliminar** | Pide confirmación y elimina el seguro del número indicado. |
| **Listar todos** | Recarga la tabla con todos los seguros. |
| **Limpiar** | Vacía el formulario. |

Al elegir el **tipo** se habilitan solo sus campos: beneficiario y parentesco para `VIDA`, marca para `VEHICULO`. Al seleccionar una fila de la tabla, sus datos pasan al formulario para editarlos o eliminarlos.

## Documentación (Javadoc)

Todas las clases del dominio, casos de uso, puertos, servicios y adaptadores están documentadas con Javadoc.

La documentación ya generada está incluida en el proyecto: abra **[docs/apidocs/index.html](docs/apidocs/index.html)** en el navegador.

Para regenerarla después de cambiar el código:

```bash
mvn javadoc:javadoc
```

## Autor

BryanL22
