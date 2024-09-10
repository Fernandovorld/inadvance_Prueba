########## PASO 1#############


#luego de haber hecho correo el sistema debe ingresar a la consola http://localhost:8080/h2-console
y hacer el siguiente Insert en la tabla Usuario 
INSERT INTO usuario (email, name, password, fecha_creacion, fecha_modificacion)
VALUES ('fernando@example.com', 'Fernando', '$2a$10$3.NnRss04nD/y1NypBNj6ud0qvg3Slvq/UjmIfzn.knN.f5vFkaKK', NOW(), NOW());

########## PASO  2 cURL #############

################### OBTENER TOKEN 
curl --location --request GET 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=791969C53C40DBB9340CC7E2F68E09D0' \
--data-raw '{
    "email":"fernando@example.com",
    "password": "943090"
}'

############# Insertar un nuevo usuario
curl --location 'http://localhost:8080/usuariosnuevos' \
--header 'Content-Type: application/json' \
--header 'Authorization: ••••••' \
--header 'Cookie: JSESSIONID=6FDA8E7FFAD51A48ABF42879518DE019' \
--data-raw '{
  "id": 1,
  "email": "fernando@example.com",
  "name": "Fernandos",
  "password": "password123",
  "fechaCreacion": "08/09/2024 15:00:00",
  "fechaModificacion": "08/09/2024 15:00:00",
  "phones": [
    {
      "number": "122545",
      "citycode": "1254654",
      "countrycode": "321321"
    }
  ],
  "status": []
}'

################### obtner todos los usuarios
curl --location 'http://localhost:8080/api/users' \
--header 'Authorization: ••••••' \
--header 'Cookie: JSESSIONID=791969C53C40DBB9340CC7E2F68E09D0' \
--data ''

################## Actualizar los usuarios
curl --location --request PUT 'http://localhost:8080/api/userUpdate' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImZlcm5hbmRvQGV4YW1wbGUuY29tIiwic3ViIjoiZmVybmFuZG9AZXhhbXBsZS5jb20iLCJleHAiOjE3MjU5MzgzMjh9.n3IkeQaHNK_Tr7iK96ksSUzsQOk4ei9LXlFprJFm0NA' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=791969C53C40DBB9340CC7E2F68E09D0' \
--data-raw '{
    
        "id": 1,
        "email": "fernando@example.com",
        "name": "Fernando",
        "password": "$2a$10$3.NnRss04nD/y1NypBNj6ud0qvg3Slvq/UjmIfzn.knN.f5vFkaKK",
        "fechaCreacion": "09/09/2024 17:28:19",
        "fechaModificacion": "09/09/2024 17:28:19",
        "phones": [
                        {
 
                "number": "22222222222",
                "citycode": "1",
                "countrycode": "91"
            }
        ]
    
}'
