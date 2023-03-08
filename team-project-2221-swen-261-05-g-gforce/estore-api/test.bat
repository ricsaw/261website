
@prompt $[nothing]                                  &:: removes the path from commands
@title Testing the Product with cURL                &:: sets the application title

@echo [4m[36mCreate a new user[0m[0m
curl -i -X POST -H "Content-Type:application/json" "http://localhost:8080/users/add" -d "{\"name\": \"testing\", \"username\": \"tester\", \"password\":\"test\", \"isAdmin\":true,\"cart\":[],\"admin\":true}"
@echo:
@echo [36m
@pause 
@echo [0m
@cls

@echo [4m[36mTry making existing user[0m[0m
curl -i -X POST -H "Content-Type:application/json" "http://localhost:8080/users/add" -d "{\"name\": \"testing\", \"username\": \"tester\", \"password\":\"test\", \"isAdmin\":true,\"cart\":[],\"admin\":true}"
@echo:
@echo [36m
@pause 
@echo [0m
@cls

@echo [4m[36mPUT a new user in an existant id[0m[0m
 

@echo:
@echo [36m
@pause 
@echo [0m
@cls

@echo [4m[36mGET all users[0m[0m
curl -i -X GET http://localhost:8080/users

@echo:
@echo [36m
@pause 
@echo [0m
@cls

@echo [4m[36mGET specific user[0m[0m
curl -i -X GET http://localhost:8080/users/4

@echo:
@echo [36m
@pause 
@echo [0m
@cls

@echo [4m[36mTry removing user[0m[0m
curl -i -X DELETE -H "Content-Type:application/json" "http://localhost:8080/users/delete/4"
@echo:
@echo [36m
@pause 
@echo [0m
@cls

@echo [4m[36mGET product 1[0m[0m             &:: [4m underlines text, [36m sets color to blue, [0m resets the modifiers

curl -i -X GET http://localhost:8080/products/1
@echo:
@echo [36m
@pause 
@echo [0m
@cls                &:: clear screen


@echo [4m[36mGET product 99[0m[0m
curl -i -X GET http://localhost:8080/products/99

@echo:
@echo [36m
@pause 
@echo [0m
@cls


@echo [4m[36mGET product which contains "Pi"[0m[0m
curl -i -X GET http://localhost:8080/products/?name="Pi"

@echo:
@echo [36m
@pause 
@echo [0m

@cls


@echo [4m[36mGET product which contains "Ab"[0m[0m
curl -i -X GET http://localhost:8080/products/?name=Ab

@echo:
@echo [36m
@pause 
@echo [0m
@cls


@echo [4m[36mPUT a new product in an existant id[0m[0m
curl -i -X PUT -H "Content-Type:application/json" "http://localhost:8080/products" -d "{\"id\": 6, \"name\": \"Yogurt\", \"description\": \"Greak or Turkish\", \"price\": 100.0, \"quantity\": 8}"

@echo:
@echo [36m
@pause 
@echo [0m
@cls


@echo [4m[36mPUT a new product in an non-existant id[0m[0m
curl -i -X PUT -H "Content-Type:application/json" "http://localhost:8080/products" -d "{\"id\": 99, \"name\": \"Yogurt\", \"description\": \"Greak or Turkish\", \"price\": 100.0, \"quantity\": 8}"

@echo:
@echo [36m
@pause 
@echo [0m
@cls


@echo [4m[36mGET the products[0m[0m
curl -i -X GET http://localhost:8080/products

@echo:
@echo [36m
@pause 
@echo [0m
@cls


@echo [4m[36mDELETE product 99[0m[0m
curl -i -X DELETE http://localhost:8080/products/99

@echo:
@echo [36m
@pause 
@echo [0m
@cls

@echo [4m[36mDELETE product 5[0m[0m
curl -i -X DELETE http://localhost:8080/products/5

@echo:
@echo [36m
@pause 
@echo [0m
@cls

@echo [4m[36mCreate a product[0m[0m
curl -i -X POST -H "Content-Type:application/json" "http://localhost:8080/products" -d "{\"name\": \"Kulfi\", \"description\": \"Indian ice-cream\", \"price\": 2.23, \"quantity\": 1340}"
@echo:
@echo [36m
@pause 
@echo [0m
@cls

@echo [4m[36mCreate a product mutiple[0m[0m
curl -i -X POST -H "Content-Type:application/json" "http://localhost:8080/products" -d "{\"name\": \"Kulfi\", \"description\": \"Indian ice-cream\", \"price\": 2.23, \"quantity\": 1340}"
@echo:
@echo [36m
@pause 
@echo [0m
@cls

@prompt     &:: reset the command path




