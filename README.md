# DropWizard-ToDo

# Setting up the Database. 
Download mysql if you have it. 
* sudo systemctl start mysql
* Follow the commands
  - mysql -u username -pusername( mysql -uroot -proot :: this was for me)
  - create database todo
  - create the required table with below commands;

``` 
CREATE TABLE todo (
id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
task_name VARCHAR(50),
description VARCHAR(100) NOT NULL,
parent_id INTEGER,
CONSTRAINT fk_inv_product_id
FOREIGN KEY (parent_id)
REFERENCES todo (id)
ON DELETE CASCADE
);
```
* Enter dummy records ::
```
INSERT INTO `todo`.`todo`(`task_name`,`description`) VALUES ('Chinmay','ALPHAQ');
INSERT INTO `todo`.`todo`
(`task_name`,`description`,`parent_id`) VALUES ('BE','writeCode',1);
INSERT INTO `todo`.`todo`
(`task_name`,`description`,`parent_id`) VALUES ('FE','writeCode',1);
INSERT INTO `todo`.`todo`
(`task_name`,`description`,`parent_id`) VALUES ('Documentation','readMe',1);
```
* Handy commands ::
```
select * from todo;
select * from todo where parent_id is null;
drop table todo;
```

# Running the Project
* In the root folder
```
mvn clean install -DskipTests=true; 
```
* Run the jar
```
java -jar target/dropwizard-toDo-1.0-SNAPSHOT.jar server config.yml
```
Will start the server at localhost:8080

# Seeing the Result
You will be needing POSTMAN for using all the CRUD operations

* Get all the Todos :: http://localhost:8080/todos/

* Select Post Request --> Body should be raw and type JSON ::http://localhost:8080/todos
```
{
  "todo": {
      "task_name": "Health",
      "description": "Follow to tone up your body"
  },
  "activityList": [
      {
          "task_name": "Workout",
          "description": "X no of pushups Y no of pull ups"

      },
       {
          "task_name": "Eat",
          "description": "Low calorie food and High Protein intake"

      }
  ]
}
```    
* Get a particular the Todos :: http://localhost:8080/todos/{id}

* To delete a ToDo. Select DELETE request in postman and give http://localhost:8080/todos/{id}. All the activities regarding that ToDo will also be deleted (On delete cascade)

* Edit Remaining

# References
* [To learn more about DropWizard](https://javadoc.io/doc/io.dropwizard/dropwizard-project)

# License

[![license](https://img.shields.io/github/license/DAVFoundation/captain-n3m0.svg?style=flat-square)](https://github.com/DAVFoundation/captain-n3m0/blob/master/LICENSE)



