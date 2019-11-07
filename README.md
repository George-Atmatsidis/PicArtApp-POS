[![PicArtAppLogo](src/main/webapp/images/login.png?raw=true "Title")]()
# Punto de venta PicArt (PicArtApp)
Un servicio web simple para llevar el control de inventario, además de realizar ventas y rentas.

## Características
Los usuarios tienen acceso a lo siguiente al configurar este software.
  - Una base de datos con todos sus productos.
  - La habilidad de realizar una venta.
  - La habilidad de realizar una renta.
  - La habilidad de actualizar inventario.
  - La habilidad de crear nuevos usuarios.

## Limitaciones de la versión actual
  - La renta no se puede realizar realmente.
  - No se ha implementado cómo ver ventas anteriores.
  - Sólo el usuario administrador puede acceder por el momento.
  - El carrito de compras no ha sido implementado.

## Tecnologías utilizadas
* Spring Boot Framework
* MySQL
* Hibernate
* Thymeleaf
* Bootstrap
* Lombok
* Google guava
* Passay

## Pre-requisitos
  - Tener instalado Java en tu sistema 
  - MySQL (ya sea en local o rds)
  - Spring Boot (como dependencia, maven lo resolverá)
  - Un EDI que soporte Maven. (o bien, maven cli)
  
## Dónde comenzar
```bash
git clone https://github.com/xtrs84zk/pos.git
```
  - Define tus credenciales en *pos/src/main/resources/application.properties* de acuerdo a tu base de datos.
```
mvn spring-boot:run
```
##  Primeros pasos
##### Registrar un nuevo usuario : http://localhost:8080/registration
##### Iniciar sesión con dicho usuario : http://localhost:8080/login
## Menus :
//TODO fill this part

## Special Thanks
- [Nushrat Jahan Nishi](https://github.com/Nushrat-Nishi) - For her original implementation.
- [Youtube](https://www.youtube.com) - For keeping me sane while doing this.