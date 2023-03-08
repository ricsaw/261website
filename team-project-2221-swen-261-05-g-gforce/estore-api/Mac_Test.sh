#!/usr/bin/env bash

curl -i -X GET http://localhost:8080/products/1
curl -i -X GET http://localhost:8080/products/99

curl -i -X PUT -H "Content-Type:application/json" "http://localhost:8080/products" -d "{\"id\": 6, \"name\": \"Yogurt\", \"description\": \"Greek or Turkish\", \"price\": 100.0, \"quantity\": 8}"

curl -i -X GET http://localhost:8080/products/6
