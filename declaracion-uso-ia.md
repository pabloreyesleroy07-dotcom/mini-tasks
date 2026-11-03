# Declaración de Uso de Inteligencia Artificial

* **Alumno:** Pablo Reyes Leroy  
* **Proyecto:** MiniTasks API (`mini-tasks`)  
* **Módulo:** Desarrollo Web en Entorno Servidor (DWEES) - 2º DAW  
* **Curso:** 2026/2027  

---

## 1. Herramienta y Modelo Utilizado
* **Herramienta:** Google Gemini.
* **Modelo específico:** Gemini Flash (modo con razonamiento alto / thinking mode).

---

## 2. Generación de la Documentación (`.md`)
Todos los archivos de documentación en formato Markdown (`aprendizaje.md`, `autoria.md` y este propio archivo `declaracion-uso-ia.md`):
* **No son textos inventados por la IA:** Han sido generados y formateados por el modelo a partir de las **descripciones, notas técnicas, reflexiones de aprendizaje y requisitos específicos que yo mismo he ido aportando** durante la sesión de trabajo.
* **Revisión activa:** Tras su generación, he revisado, adaptado la redacción y validado que el contenido refleja fielmente las decisiones técnicas que tomé durante la práctica y cómo funciona mi código.

---

## 3. Asistencia en el Desarrollo del Código

El modelo de IA se ha utilizado como un asistente técnico y tutor interactivo en los siguientes puntos:

1. **Metodología TDD:** Guía paso a paso para seguir el ciclo estricto Red-Green (asegurar el fallo de compilación/ejecución con `MockMvc` antes de crear la implementación).
2. **Evolución arquitectónica:** Planteamiento guiado para pasar de la versión inicial en memoria (V1) a la arquitectura profesional desacoplada (V4: Controlador -> Servicio -> Repositorio -> Base de datos H2).
3. **Depuración y resolución de incidencias:**
   * Detección de errores de ámbito (*scope*) en Maven, como la colocación accidental de clases de prueba en `src/main/java` en vez de `src/test/java`.
   * Corrección de rutas de trabajo al crear accidentalmente archivos en otro proyecto del workspace (`battleship`).
   * Resolución de inconsistencias tipográficas entre carpetas y paquetes Java (`minitask` vs `minitasks`).

---

## 4. Control de Autoría y Defensa Técnica
* Ningún cambio se ha integrado a ciegas: cada paso ha sido ejecutado en mi entorno local sobre Debian Linux Crostini y verificado mediante `./mvnw test` con salida limpia (`BUILD SUCCESS`).
* Conozco y comprendo el propósito de cada clase, interfaz, anotación (`@RestController`, `@Service`, `@Transactional`, `@ResponseStatus`) y `record` empleado.
* Asumo la autoría total del repositorio y estoy preparado para explicar, justificar y modificar cualquier parte de la solución.