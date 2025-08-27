💻 Inditex - Prueba Técnica Java Senior

Hola! 👋 Este es mi proyecto para la prueba técnica de Senior Java Backend Developer en Inditex.
🎯 ¿Qué hace esta API?

Es un servicio Spring Boot que te dice qué precio aplicar a un producto de Zara en un momento específico. Como a veces hay varias tarifas para el mismo producto, la API elige la correcta basándose en la prioridad.
🧠 El problema que resuelve

Imagina que Zara tiene diferentes precios para el mismo producto:

    Precio normal: 35.50 € (válido casi todo el año)

    Precio especial por la tarde: 25.45 € (solo de 15:00 a 18:30)

    Ofertas de fin de semana: 30.50 €

    etc...

Mi API te dice exactamente qué precio aplicar en cualquier momento.
🚀 Cómo probarlo
Opción 1: Con curl (desde terminal)
bash

# Test 1: 10:00 del 14 de junio
curl "http://localhost:8080/api/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1"

# Test 2: 16:00 del 14 de junio
curl "http://localhost:8080/api/prices?date=2020-06-14T16:00:00&productId=35455&brandId=1"

# Test 3: 21:00 del 14 de junio
curl "http://localhost:8080/api/prices?date=2020-06-14T21:00:00&productId=35455&brandId=1"

Opción 2: Desde el navegador

Abre http://localhost:8080/swagger-ui.html para tener una interfaz bonita donde probar la API.
Opción 3: Con Postman

Importa esta colección:
json

{
"requests": [
{
"name": "Test 1 - 10:00 día 14",
"url": "http://localhost:8080/api/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1"
}
]
}

📦 Lo que he implementado
✅ Funcionalidad completa

    Endpoint REST que encuentra el precio correcto

    Base de datos H2 con los datos de ejemplo

    Todos los tests pedidos funcionando

    Manejo de errores cuando no encuentra precios

✅ Arquitectura limpia

He usado Arquitectura Hexagonal para que el código sea mantenible:

    domain/ → La lógica de negocio (sin depender de frameworks)

    application/ → Los casos de uso

    infrastructure/ → Spring, base de datos, etc.

✅ Tests que pasan

Los 5 casos de prueba requeridos:
Test	Hora	Precio Esperado	✅
Test 1	2020-06-14 10:00	35.50 €	✓
Test 2	2020-06-14 16:00	25.45 €	✓
Test 3	2020-06-14 21:00	35.50 €	✓
Test 4	2020-06-15 10:00	30.50 €	✓
Test 5	2020-06-16 21:00	38.95 €	✓
🛠️ Tecnologías que uso

    Java 17 - Porque me gusta estar actualizado 😄

    Spring Boot 3 - El framework más sólido

    H2 Database - Base en memoria para pruebas

    JUnit 5 & Mockito - Para tests robustos

    Swagger - Documentación automática de la API

🏗️ Cómo está organizado el código
text

src/
├── domain/           # Lo más importante: reglas de negocio
├── application/      # Casos de uso y servicios  
└── infrastructure/   # Spring, base de datos, web

Lo bonito: El dominio no sabe nada de Spring o bases de datos. Esto hace el código más testeable y mantenible.
🧪 Para ejecutar los tests
bash

# Todos los tests
mvn test

# Ver cobertura de tests
mvn jacoco:report

🎨 Algunas decisiones técnicas interesantes
1. 🎯 Prioridad de precios

Cuando hay varios precios para la misma fecha, uso el de mayor prioridad (así me lo pedían).
2. 🗃️ Base de datos en memoria

Uso H2 para que sea fácil de probar, pero la arquitectura permitiría cambiar a MySQL/PostgreSQL fácilmente.
3. ✅ Tests realistas

No solo tests unitarios, sino también tests de integración que prueban desde el controller hasta la base de datos.
🔍 ¿Quieres ver la base de datos?

Mientras la app está corriendo:

    Ve a http://localhost:8080/h2-console

    Usuario: sa

    Password: (déjalo vacío)

    JDBC URL: jdbc:h2:mem:testdb

Verás la tabla PRICES con los datos de ejemplo.
📝 Si quieres contribuir o probar
bash

# Clona el proyecto
git clone <url>
cd bcnc-inditex

# Ejecuta los tests
mvn test

# Levanta la aplicación
mvn spring-boot:run

🚀 Próximos pasos (si tuviera más tiempo)

    Cachear consultas frecuentes

    Métricas con Micrometer

    Dockerizar la aplicación

    Logs más detallados

📞 ¿Preguntas?

Si algo no está claro o quieres que explique alguna parte del código, no dudes en preguntar!

¡Espero que te guste mi implementación! 🚀