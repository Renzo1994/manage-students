# manage-students
A project to manage student data
 

## POST /v1/alumnos/crear
Crea un alumno.

### Body:
{
  "id": "1",
  "nombre": "Juan",
  "apellido": "Perez",
  "estado": "ACTIVO",
  "edad": 20
}

### Respuestas:
- 200 OK
- 400 Error de validación
- 409 Alumno ya existe
