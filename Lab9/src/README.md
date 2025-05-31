# Geography REST API

This is a Spring Boot application that provides REST APIs for managing countries and cities data.

## Features

- Full CRUD operations for Countries and Cities
- Search functionality
- Swagger/OpenAPI documentation
- PostgreSQL database integration
- API client for testing

## Requirements

- Java 17+
- Maven 3.6+
- PostgreSQL database

## Setup

1. **Database Setup**
   ```sql
   -- Create database (if not exists)
   CREATE DATABASE postgres;
   ```

2. **Update Configuration**
   Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

### Countries

- `GET /api/countries` - Get all countries
- `GET /api/countries/{id}` - Get country by ID
- `POST /api/countries` - Create new country
- `PUT /api/countries/{id}` - Update country
- `DELETE /api/countries/{id}` - Delete country
- `GET /api/countries/search?name={name}` - Search countries by name

### Cities

- `GET /api/cities` - Get all cities
- `GET /api/cities/{id}` - Get city by ID
- `POST /api/cities` - Create new city
- `PUT /api/cities/{id}` - Update city
- `DELETE /api/cities/{id}` - Delete city
- `GET /api/cities/search?name={name}` - Search cities by name
- `GET /api/cities/capitals` - Get all capital cities
- `GET /api/cities/country/{countryId}` - Get cities by country

## Testing

### Browser Testing
- Open browser and navigate to: `http://localhost:8080/api/countries`

### Swagger UI
- Access Swagger documentation at: `http://localhost:8080/swagger-ui.html`
- API docs JSON available at: `http://localhost:8080/api-docs`

### Postman Testing

#### Get All Countries
```
GET http://localhost:8080/api/countries
```

#### Create New City
```
POST http://localhost:8080/api/cities
Content-Type: application/json

{
    "name": "New City",
    "capital": false,
    "latitude": 45.0,
    "longitude": 25.0,
    "country": {
        "id": 1
    }
}
```

#### Update City Name
```
PUT http://localhost:8080/api/cities/1
Content-Type: application/json

{
    "id": 1,
    "name": "Updated City Name",
    "capital": false,
    "latitude": 45.0,
    "longitude": 25.0
}
```

#### Delete City
```
DELETE http://localhost:8080/api/cities/1
```

## API Client Usage

The application includes a built-in API client (`ApiClient.java`) that demonstrates how to consume the REST services:

```java
@Autowired
private ApiClient apiClient;

// Get all countries
List<Country> countries = apiClient.getAllCountries();

// Create new city
City city = new City("Test City", false, 45.0, 25.0);
City created = apiClient.createCity(city);

// Update city
created.setName("Updated Name");
apiClient.updateCity(created.getId(), created);

// Delete city
apiClient.deleteCity(created.getId());
```

## Architecture

The application follows the MVC (Model-View-Controller) pattern:

- **Model**: Entity classes (`City`, `Country`, `Continent`)
- **View**: REST API endpoints (JSON responses)
- **Controller**: REST controllers (`CityController`, `CountryController`)
- **Service**: Business logic layer (`CityService`, `CountryService`)
- **Repository**: Data access layer (Spring Data JPA repositories)

## Sample Data

The application automatically initializes sample data on startup:
- Continents: Europe, Asia, North America
- Countries: Romania, France, Japan, United States
- Cities: Bucharest, Paris, Tokyo, New York, etc.

## Technologies Used

- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Swagger/OpenAPI 3
- Maven