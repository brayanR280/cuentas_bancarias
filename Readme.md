## Ejecución proyecto
Se presentan diferentes opciones para la ejecución del microservicio. A continuación se detallan los diferentes metodos de ejecución:
### Opción 1: Ejecutar con Gradle
1. Clonar el repositorio https://github.com/brayanR280/cuentas_bancarias.git
2. Abrir una terminal en la raíz del proyecto.
3. Ejecutar el siguiente comando para compilar y ejecutar la aplicación:
```bash
   ./gradlew bootRun
   ```
5. La aplicación estará disponible en http://localhost:8080.
6. Para detener la aplicación, presione `Ctrl + C` en la terminal.

### Opción 2: Ejecutar con Docker Compose
1. Asegúrese de tener Docker y Docker Compose instalados.
2. Clonar el repositorio https://github.com/brayanR280/cuentas_bancarias.git.
3. Abrir una terminal en la raíz del proyecto.
4. Ejecutar el siguiente comando para construir y levantar los contenedores:
```bash
   docker compose up --build
   ```
5. La aplicación estará disponible en http://localhost:8080.
6. Para detener los contenedores, presione `Ctrl + C` en la terminal
7. Para eliminar los contenedores y la red creada, ejecute:
```bash
   docker-compose down
   ```