# Pricing API – Prueba Técnica

Este proyecto implementa un servicio REST en **Java 17** con **Spring Boot 3**, siguiendo principios de **arquitectura hexagonal**.  
El objetivo es exponer un endpoint que devuelva el precio aplicable de un producto en una fecha concreta para una cadena (brand).

---

## 🚀 Cómo arrancar el proyecto

1. Descomprimir el zip.
2. Entrar a la carpeta del proyecto:
   ```bash
   cd bcnc-inditex
   ```
3. Ejecutar con Maven:
   ```bash
   mvn spring-boot:run
   ```
   La aplicación arranca en `http://localhost:8080`.
4. Definición API (Swagger)
   ```bash
   http://localhost:8080/swagger-ui.html
   ```
---

## 📌 Endpoint disponible

//http://localhost:8080/api/v1/prices?date=2020-06-14T00:00:00&productId=35455&brandId=1
`GET /api/v1/prices`

**Parámetros de entrada (query params):**
- `date`: fecha en formato ISO (ejemplo: `2020-06-14T10:00:00`)
- `productId`: id del producto (ejemplo: `35455`)
- `brandId`: id de la cadena (ejemplo: `1`)

**Ejemplo de llamada:**
```
GET http://localhost:8080/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1
```

**Respuesta de ejemplo (200 OK):**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

**Respuesta en caso de no encontrar precio (404):**
```
No price found
```

---

## 🗄️ Base de datos

- Se usa **H2 en memoria**.
- La tabla `prices` se inicializa automáticamente al arrancar con los datos del enunciado.
- Consola de H2 disponible en `http://localhost:8080/h2-console`.
    - JDBC URL: `jdbc:h2:mem:pricingdb`
    - Usuario: `sa`
    - Password: `password`

---

## ✅ Tests

Se han implementado dos tipos de pruebas:

1. **Unitarias**
    - Testean la lógica de negocio en `PriceService` (selección por prioridad y fecha más reciente).

2. **Integración**
    - Testean el endpoint `/prices` con la base H2 y los cinco escenarios definidos en la prueba:

    - 14/06/2020 10:00 → tarifa 1
    - 14/06/2020 16:00 → tarifa 2
    - 14/06/2020 21:00 → tarifa 1
    - 15/06/2020 10:00 → tarifa 3
    - 16/06/2020 21:00 → tarifa 4

Se pueden ejecutar con:
```bash
mvn test -Dspring.profiles.active=test
```

Para ejecutar las validaciones de checkstyle y spotbugs
```bash
mvn clean test checkstyle:check spotbugs:check
```

Reporte HTML
```bash
mvn checkstyle:checkstyle spotbugs:spotbugs
# Reportes en: target/site/checkstyle.html y target/site/spotbugs.html
```

---

## 🏗️ Arquitectura

El proyecto sigue **arquitectura hexagonal**:

- **domain**:
    - Entidad `Price`
    - Puerto `PriceRepositoryPort`

- **application**:
    - Caso de uso `PriceService` con la lógica de selección de tarifas

- **infrastructure**:
    - Adaptador JPA (`PriceEntity`, `SpringDataPriceRepository`, `JpaPriceRepository`)
    - Adaptador REST (`PriceController`, `PriceResponse`)
    - Configuración de beans

Con esta separación, la lógica de negocio es independiente de la base de datos y del framework web.