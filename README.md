# api-controle-freelancer
 Api for financial control of a freelancer worker.

## 1. Stack

1. Java 8
2. Spring Boot, Security, Jpa
3. Junit
4. Mockito
5. Docker


## 2. URLs
* **baseURL**
 1. local -> http://localhost:8080/api/v1

## 3. Running
1. Clone repository.
2. Access folder project.
3. Generate jar --> Maven Build: clean package
4. in Terminal:
```
docker build -t api-controle-freelancer:v1 .
docker run -p 8080:8080 api-controle-freelancer:v1
```
5. Access Swagger --> baseURL/swagger-ui.html
6. Request Endpoints

## 4. EndPoints
### a. Authentication Endoint
* **POST** baseURL/auth - Endpoint for Authentication

Important: User for login 
* **email**: marcusdavi@gmail.com
* **password**: admin

Request
```
{
  "email": "marcusdavi@gmail.com",
  "senha": "admin"
}
```

Response:
```
{
  "token": {
    "type": "string",
    "value: "string"
  },
  "user": {
    "name": "string",
    "email": "string",
    "cnpj": "string",
    "companyName": "string",
    "phoneNumber": "string"
  }
}
```

**Important!**: Pass the token generated in the authentication endpoint in the all requests, except the /auth: 
Bearer token

### b. Categories Endoints
* **GET** baseURL/categories - List with all categories;
* **GET** baseURL/categories/{id} - Detail the category with param id;
* **GET** baseURL/categories/search?name=NAME - List the customers with name containing string NAME;
* **PUT** baseURL/categories/{id}/archives - Logical exclusion the category with param id;
* **POST** baseURL/categories - create category;
* **PUT** baseURL/categories/{id} - update category.

In the POST and in the PUT, the registration/change data of the category must be passed:

Request
```
{
  "description": "string",
  "name": "string"
}
```

### c. Customers Endoints
* **GET** baseURL/customers - List with all customers;
* **GET** baseURL/customers/{id} - Detail the customer with param id;
* **GET** baseURL/customers/search?commercialName=NAME&cnpj=CNPJ - List the customers with commercialName containing string NAME and cnpj equals CNPJ;
* **PUT** baseURL/customers/{id}/archives - Logical exclusion the customer with param id;
* **POST** baseURL/customers - create customer;
* **PUT** baseURL/customers/{id} - update customer.

In the POST and in the PUT, the registration/change data of the customer must be passed:

Request
```
{
  "cnpj": "string",
  "commercialName": "string",
  "legalName": "string"
}
```

### d. Users Endoints
* **GET** baseURL/users - List with all users;
* **GET** baseURL/users/{id} - Detail the user with param id;
* **GET** baseURL/users/search?name=NAME - List the users with name containing string NAME;
* **POST** baseURL/users - create user;
* **PUT** baseURL/users/{id} - update user.

In the POST and in the PUT, the registration/change data of the user must be passed:

Request
```
{
  "cnpj": "string",
  "companyName": "string",
  "email": "string",
  "name": "string",
  "password": "string",
  "phoneNumber": "string"
}
```

### e. Revenues Endoints
* **GET** baseURL/revenues - List with all revenues;
* **GET** baseURL/revenues/{id} - Detail the revenue with param id;
* **POST** baseURL/revenues - create revenue;
* **PUT** baseURL/revenues/{id} - update revenue.

In the POST and in the PUT, the registration/change data of the revenue must be passed:

### f. Expenses Endoints
* **GET** baseURL/expenses - List with all expenses;
* **GET** baseURL/expenses/{id} - Detail the expense with param id;
* **POST** baseURL/expenses - create expense;
* **PUT** baseURL/expenses/{id} - update expense.

In the POST and in the PUT, the registration/change data of the expense must be passed:

### g. Reports Endoints
* **POST** baseURL/total-revenue - List with all revenues of year;
* **POST** baseURL/revenue-by-month - lists all revenue for the year grouped by month;

Request POST
```
{
  "fiscalYear": 2020,
}
```

**Tip**: There is data for the year 2020

### h. Settings Endoints
* **GET** baseURL/settings - List with all system settings;
* **PUT** baseURL/settings - update system settings.

Request PUT
```
{
  "emailNotification": true,
  "maxRevenueAmount": 80000.20,
  "smsNotification": true
}
```

