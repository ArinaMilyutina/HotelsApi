# **HotelsApi**

RESTful API application for working with hotels. It has the following methods: create hotels, find all hotels, find
hotel by id,
search hotel by criteria, add amenities to the hotel and count hotels by parameter.

## **Technology stack:**

- Java 17
- Spring Boot
- Spring Data JPA
- H2
- Liquibase
- MapStruct
- Maven
- JUnit
- Mockito
- Lombok
- Swagger

## **Run the project**

1. git clone https://github.com/ArinaMilyutina/HotelsApi
2. cd HotelsApi
3. mvn clean install
4. mvn spring-boot:run

## **Collection of request for testing (Postman):**

##### 1. POST, URL:http://localhost:8092/property-view/hotels

    json:
    
     {
			"name": "DoubleTree by Hilton Minsk",
			"description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...", 
			"brand": "Hilton",
			"address": 
				{
					"houseNumber": 9,
					"street": "Pobediteley Avenue",
					"city": "Minsk",
					"county": "Belarus",
					"postCode": "220004"
				},
			"contacts": 
				{
					"phone": "+375 17 309-80-00",
					"email": "doubletreeminsk.info@hilton.com"
				},
			"arrivalTime":
				{
					"checkIn": "14:00",
					"checkOut": "12:00" 
				}
		}

##### 2. GET, URL:http://localhost:8092/property-view/hotels/{id}

##### 3. POST, URL:http://localhost:8092/property-view/hotels/{id}/amenities

    json:
    
    [
      "Free parking",
      "Free WiFi",
      "Non-smoking rooms",
      "Concierge",
      "On-site restaurant",
      "Fitness center",
      "Pet-friendly rooms",
      "Room service",
      "Business center",
      "Meeting rooms"
    ]

##### 4. GET, URL:http://localhost:8092/property-view/search?city=minsk (name, brand, city, county, amenities)

##### 5. GET, URL:http://localhost:8092/property-view/hotels

##### 6. GET, URL:http://localhost:8092/property-view/histogram/{param} (param=brand, city, county, amenities)

##### 7. Swagger:

    http://localhost:8092/swagger-ui/index.html,
    http://localhost:8092/v3/api-docs
