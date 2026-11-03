# Aprendizaje - MiniTasks

En esta práctica he desarrollado la API MiniTasks desde cero siguiendo la metodología TDD y avanzando paso a paso desde una versión simple en memoria hasta una arquitectura en capas con persistencia en H2.

---

## 1. Evolución de la arquitectura: De V1 (memoria) a V4 (capas)

Al principio, en la versión V1, metí una lista en memoria (`ArrayList`) dentro del propio controlador `TaskController`. Servía para probar rápido que las rutas respondían y que el formato JSON era el correcto, pero tenía problemas evidentes:
* Si reiniciaba el servidor, los datos se borraban.
* El controlador hacía de todo: recibía la petición HTTP, buscaba en la lista y devolvía directamente la entidad.

Para pasar a la arquitectura V4 separé las responsabilidades en cuatro capas bien diferenciadas:
1. **Controlador (`TaskController`):** Solo se encarga de la parte web (mapeo de rutas, códigos HTTP como 200, 201 o 404 y recibir/devolver DTOs). No tiene lógica de negocio.
2. **Servicio (`TaskService`):** Es el cerebro de la aplicación. Gestiona la lógica, coordina las llamadas al repositorio, maneja las transacciones con `@Transactional` y se encarga de convertir entidades a DTOs.
3. **Repositorio (`TaskRepository`):** Interfaz que extiende de `JpaRepository`. No hace falta implementar métodos a mano porque Spring Data genera el CRUD básico y resuelve consultas derivadas como `findByDone(boolean done)`.
4. **Dominio (`Task`):** La entidad JPA que mapea la tabla `tasks` en la base de datos H2 con sus anotaciones (`@Entity`, `@Id`, `@GeneratedValue`).

---

## 2. Por qué usamos DTOs con `record` y no la Entidad directamente

En vez de recibir o devolver la clase `Task` en el controlador, creé dos DTOs (`TaskRequest` y `TaskResponse`) usando `record`:
* **Seguridad y control:** Si expongo la entidad directamente en un POST, alguien podría intentar mandar un `id` o modificar campos internos que no le corresponden. Con `TaskRequest` controlo que solo me puedan mandar el `title`.
* **Desacoplamiento:** Si el día de mañana cambio un tipo de dato en la base de datos o renombro una columna en la entidad, no rompo el contrato JSON que consumen los clientes.
* **Inmutabilidad:** Los `record` de Java son inmutables por defecto, no tienen setters y ahorran escribir constructores o getters a mano. Con el método estático `TaskResponse.fromEntity(task)` centralizo la transformación de la entidad al DTO en un único punto.

---

## 3. Manejo de errores y el código 404

Para cumplir con el contrato REST, si alguien pide una tarea con un ID que no existe no debemos devolver un error 500 ni un cuerpo vacío. 

Creé la excepción `TaskNotFoundException` anotada con `@ResponseStatus(HttpStatus.NOT_FOUND)`. Cuando en el servicio hacemos `taskRepository.findById(id).orElseThrow(...)`, Spring intercepta automáticamente esa excepción y responde al cliente con un código 404 limpio.

---

## 4. TDD y Pruebas con MockMvc

He trabajado aplicando el ciclo Rojo-Verde de TDD:
1. **Fase Red:** Escribir primero el test antes de programar la funcionalidad. Por ejemplo, en el controlador escribí el test que pedía la tarea 1 esperando un 200; como la ruta no estaba implementada, fallaba con 404.
2. **Fase Green:** Escribir el código mínimo necesario para que el test compile y pase en verde.

Para las pruebas del controlador utilicé `MockMvc` dentro de `TaskControllerTest`. Esto permite simular llamadas HTTP reales contra Spring sin tener que abrir el navegador ni Postman:
* Compruebo el código de estado con `status().isOk()` o `status().isNotFound()`.
* Valido el contenido exacto del JSON con `jsonPath("$.title")` o `jsonPath("$.done")`.
* Probé el corte vertical (*slice*) completo añadiendo el test del filtro `GET /v1/tasks?done=true`, verificando que la petición atraviesa el controlador, el servicio, la consulta derivada del repositorio y la base de datos H2.