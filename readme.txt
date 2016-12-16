CRUD carteleras info entidad elegida Comentario



mapeos:
[POST]http://localhost:8080/cartelerasInfo/comentario
{
"texto":"texto",
"fecha":1312444443453,
"creador_id":123,
"anuncio_id":234,
}

[PUT]http://localhost:8080/cartelerasInfo/comentario/{id}
{
"texto":"texto",
"fecha":1312444443453,
"creador_id":123,
"anuncio_id":234,
}

[DELETE]http://localhost:8080/cartelerasInfo/comentario/{id}
[GET]http://localhost:8080/cartelerasInfo/comentario/{id}
[GET]http://localhost:8080/cartelerasInfo/comentario



REST guarani
[GET]http://localhost:8080/guarani/alumnos
[GET]http://localhost:8080/guarani/alumnos/{id}
[POST]http://localhost:8080/guarani/alumnos/chequearlogin
{
"id":1,
"password":"password"
}

[GET]http://localhost:8080/guarani/profesores
[GET]http://localhost:8080/guarani/profesores/{id}
[POST]http://localhost:8080/guarani/profesores/chequearlogin
{
"id":1,
"password":"password"
}


