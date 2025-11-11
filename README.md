#Booksy SPA

## 1. Caso elegido y alcance 

**Caso elegido y alcance:** Booksy es una plataforma movil que se encarga de la visualizacion y gestion de libros de formato digital de varios escritores independientes de la escena chilena. 
**Descripcion del caso:** permite que un usuario pueda iniciar sesión dentro de la app, navegar por distintas pantallas y visualizar contenido relacionado con libros digitales. El foco está en ofrecer una experiencia simple y fluida para acceder a libros desde un dispositivo móvil.
**Problemática que aborda:** Muchas aplicaciones de lectura requieren conexión constante o no guardan preferencias del usuario. Booksy busca simplificar el acceso a contenido digital almacenando datos localmente (sesión y preferencias).

## 2. Requsitos y ejecuccion
*Stack Usado en le proyecto:**  
  
"Kotlin" (Android)
"Jetpack Compose" (UI declarativa)
"Navigation-Compose" (flujo entre pantallas)
"DataStore Preferences" (persistencia local)
"Coil" (carga de imágenes desde internet)

**Instalación:**
  
```bash
git clone https://github.com/gonzalopalma22/desarrollloapps

**Ejecuccion**
Abrir el proyecto en Android Studio Hedgehog o superior
Seleccionar un emulador o dispositivo físico con Android API 26+
Ejecutar el proyecto desde en Android Studio con las opciones de:
Run > Run 'app'

## 3. Arqueitectura y flujo

**Estructura de carpetas:** 
  
Organización modular según responsabilidad:  
- `ui/` → pantallas y componentes Jetpack Compose  
- `navigation/` → configuración del flujo y rutas  
- `datastore/` → persistencia local (preferencias del usuario)  
- `model/` → modelos de datos (entidades utilizadas en las pantallas)

**Gestión de estado:**  
  
El estado de los formularios y pantallas se maneja de manera local dentro de cada componente utilizando `remember` + `mutableStateOf`, garantizando que los cambios del usuario se reflejen inmediatamente en la UI.

**Navegación:** 
  
Implementada con **Navigation Compose**, permitiendo:  
- Stack de pantallas con `NavHost`  
- Transición entre pantallas mediante rutas  
- Manejo del backstack desde cualquier vista

## 4. Funcionalidades

**Formulario validado**: Validación de campos en el login/registro antes de permitir el acceso del usario a la aplicacion.
**Navegación y backstack**: Flujo de pantallas controlado con Navigation Compose.
**Gestión de estado**: Manejo de estados de éxito/error y el feedback  visual en pantalla.
**Persistencia local (DataStore)**: Se guarda información que ha sido entregada por el usuario (ej: sesión iniciada, credenciales y preferencias).
**Uso de recursos nativos**:  
-Acceso a galería mediante intent (selección de imagen)
-Manejo de permisos y fallback

## 5. Endpoints (cambiar)

**Base URL (futura):** `https://api.booksy.cl/v1`

| Método | Ruta        | Body (JSON)                               | Respuesta esperada |
|--------|-------------|--------------------------------------------|---------------------|
| POST   | `/auth/signup` | `{ "email": "...", "password": "...", "name": "..." }` | `201 Created` → `{ "authToken": "...", "user": { "id": 1, "name": "...", "email": "..." } }`
| POST   | `/auth/login`  | `{ "email": "...", "password": "..." }`                 | `200 OK` → `{ "authToken": "...", "user": { "id": 1, "name": "...", "email": "..." } }`
| GET    | `/auth/me`     | - requiere Header: `Authorization: Bearer <token>`      | `200 OK` → `{ "id": 1, "name": "...", "email": "...", "avatarUrl": "..." }`

## 6. User flows (flujo del usuario)

**Flujo principal (login → home → logout)**

1. El usuario abre la aplicación.
2. Se muestra pantalla de **Login**.
3. El usuario ingresa tanto su correo y contraseña.
4. Se valida formulario ( que no hayan campos vacíos y formato correcto).
5. Si pasa validación:
   - Se guarda el estado de sesión en **DataStore**.
   - Se navega a la **pantalla Home**.
6. En Home puede:
   - Explorar libros disponibles (mock en esta versión).
   - Acceder al perfil y cargar una imagen desde la galería.
7. Desde el menú puede cerrar sesión:
   - DataStore borra los datos persistidos.
   - Se vuelve a la pantalla de Login.

**Casos de error manejados:**

- Campos vacíos en login → mensaje de error en pantalla.
- Formato de correo inválido → bloqueo de avance.
- Cancelar selección de imagen en galería → se muestra fallback.
