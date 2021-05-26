# Readme

## 1. Tecnologías

- Java 11
- Spring
- Maven
- Feign
- JUnit, Mockito, RestAssured
- MySQL
- Docker Compose

## 2. Arquitectura

La arquitectura de la aplicación es multicapa (capa de presentación, capa de lógica de negocio, capa de acceso a datos). En concreto, la capa de acceso a datos podría dividirse en dos: almacenamiento de imágenes y persistencia en DB. Debido a esto, la arquitectura tiene margen de mejora, migrándola a arquitectura hexagonal.

## 3. Endpoints

|   #   | Endpoint                       | Descripción                                                                                  |
| :---: | :----------------------------- | :------------------------------------------------------------------------------------------- |
|   1   | `POST /v1/signup`              | Registra un usuario.                                                                         |
|   2   | `POST /v1/signin`              | Devuelve el token asociado al usuario, comprobando username/password. El token nunca expira. |
|   3   | `POST /v1/tickets`             | Crea un ticket.                                                                              |
|   4   | `GET /v1/tickets`              | Listar tickets, con paginado y filtros por fecha de creación y por estado.                   |
|   5   | `GET /v1/tickets/{id}`         | Devuelve un ticket.                                                                          |
|   6   | `POST /v1/tickets/{id}/images` | Sube una imagen a un ticket. La imagen va en Base64.                                         |

## 4. Seguridad

El endpoint `1` almacena la contraseña del usuario en claro, lo cual es un problema de seguridad. Lo ideal sería mediante salted hashes.

Todos los endpoints salvo el `1` y el `2` requieren la cabecera `Authorization` con el Bearer token (ej.: `Authorization: Bearer 6352ce54-0512-4f11-9349-29914f44fbe8`). La implementación tiene mucho margen de mejora, utilizando Spring Security o quizá evolucionando hacia OAuth2.

Para obtener el token es necesario crear un usuario con el endpoint `1` y hacer login con el endpoint `2`.

## 5. Subida de imágenes

Para el endpoint `6` he utilizado futuros de Java, mediante la anotación `@Async` de Spring. La tarea se lanza en segundo plano en un hilo, respondiendo inmediatamente a la petición con un `HTTP 202 Accepted`.

Para almacenar la imagen se utiliza la API REST pública de Cloudinary (no he realizado pruebas reales, pero he utilizado la documentación de <https://cloudinary.com/documentation/upload_images#uploading_with_a_direct_call_to_the_rest_api>).
