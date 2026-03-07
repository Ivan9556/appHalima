### Halima App ![androide.png](app/src/main/res/drawable/androide.png)

Este repositorio contiene la aplicación móvil del ecosistema Halima, desarrollada de forma nativa
para Android utilizando Java. La aplicación permite interactuar con los servicios de gestión 
de cuevas, consumiendo datos de una API centralizada.

Nota: Este proyecto se encuentra actualmente en fase de desarrollo.

### **Ecosistema del Proyecto**
Esta aplicación es el cliente móvil de un sistema integral que incluye:

* **Web/Backend:** [cuevasHalima](https://github.com/Ivan9556/cuevasHalima.git) (Desarrollado con 
Python, HTML, CSS y JS).

### **Tecnologías Utilizadas**

* **Lenguaje:** Java (Android SDK).
* **Redes:** [Retrofit2](https://square.github.io/retrofit/) para las peticiones HTTP.
* **Serialización:** GSON para el mapeo de objetos JSON.
* **Seguridad:** Autenticación basada en Tokens (JWT).

### **Tecnologías Utilizadas**

* Integración de Login y Registro de usuarios.
* Gestión de seguridad mediante Interceptors de Retrofit para el manejo de Tokens.
* Visualización de datos de cuevas en tiempo real desde el backend.

### **Arquitectura de Red**

La aplicación utiliza Retrofit para comunicarse con el servidor Python. 
El flujo de validación sigue este esquema:

* **Petición de Login:** El usuario envía credenciales.
* **Validación:** El servidor devuelve un Token de acceso.
* **Persistencia:** La App almacena el token de forma segura.
* **Peticiones Autorizadas:** Se adjunta el token en el Header Authorization de cada petición 
posterior.

