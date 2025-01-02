# ForumFlow Backend

ForumFlow es una plataforma moderna y escalable para la gestión de comunidades en línea, desarrollada con **Spring Boot**. A lo largo del desarrollo, se implementan conceptos clave como microservicios, operaciones CRUD, sistemas de mensajería, moderación automática y autenticación basada en JWT.  

> **Nota:** Este proyecto aún está en desarrollo. Algunas funcionalidades pueden estar incompletas o sujetas a cambios.  

## Contenido del Proyecto  

1. **Configuración del Proyecto**  
   - Creación inicial del proyecto utilizando Spring Boot y configuración del ecosistema de microservicios.  

2. **Microservicios Core**  
   - **Servicio de usuarios**: Gestión de usuarios, roles y autenticación JWT.  
   - **Servicio de foro**: Creación y gestión del foro, incluyendo post, comentarios y respuestas.  
   - **Servicio de moderación**: Sistema híbrido de moderación automática y manual.  
   - **Servicio de multimedia**: Gestión archivos multimedia y su distribución.  
   - **Servicio de comunicación**: Sistema de chat en tiempo real entre usuarios.  

3. **Integración de Infraestructura**  
   - Registro de servicios con Eureka y enrutamiento centralizado con API Gateway.  
   - Uso de Kafka para eventos y comunicación asincrónica entre servicios.  

4. **Monitoreo y Resiliencia**  
   - Configuración de Prometheus y Grafana para monitoreo del sistema.  
   - Implementación de patrones de resiliencia como Circuit Breaker.  

## Funcionalidades Principales  

- **Gestión de usuarios**  
   - Autenticación y autorización con JWT.  
   - Roles y permisos personalizados.  

- **Gestión del foro**  
   - CRUD para post, comentarios y respuestas.  
   - Votaciones y búsqueda avanzada.  

- **Moderación de contenido**  
   - Análisis híbrido de contenido con Azure AI Content Safety y moderadores.  
   - Gestión de reportes y acciones de moderación manual.  

- **Comunicación entre usuarios**  
   -  Chat privado y grupal, notificaciones push, historial de mensajes.  

- **Servicio multimedia**  
   - Carga y procesamiento de archivos, optimización de imágenes, gestión de metadatos.

## Tecnologías Utilizadas  

- **Java 17**  
- **Spring Boot**  
- **Spring Security**  
- **MongoDB**, **PostgreSQL**, y **Cassandra** para almacenamiento.
- **Redis** para caché.
- **Kafka** para mensajería y eventos.  
- **Docker** y **Kubernetes** para contenedores y despliegue.  
- **Prometheus** y **Grafana** para monitoreo.  
- **Azure AI Content Safety** para moderación automática.  

¡Gracias por explorar **ForumFlow**! 🚀  
