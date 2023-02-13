# SPVA SHOP
It's my POV how should look web-shop. Of course mainly back-end part, I am poorly front-end skilled XD.


##How to run?
To run app you must have database, so run in cmd:

```bash
docker-compose up -d
```
After that you are ready to go to run this app. You can also use [PGADMIN4](https://localhost:5050) to view and manage db. 
See [How to use pgadmin4?](#pg_admin_usage)

```bash
login : pgadmin4@pgadmin.org
password : adming
```
After running app its available at [SPVA-SHOP](https://localhost:8080)

For admin:
```bash
login : admin
password : admin
```
For normal user:
```bash
login : customer
password : customer
```
<a name="pg_admin_usage"></a>
##How to use pgadmin4? 
PgAdmin4 -> Add sever

```bash
name : shop

host : container-postgresdb

username : shop

password : shop
```

#### Why _"SPVA SHOP"_?
It's because two major technologies whose were used to create this app starts with Sp and Va.