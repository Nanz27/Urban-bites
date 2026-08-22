# 🍽️ Urban Bites [GROUP-01]

**Urban Bites** is a JavaFX-based food ordering service developed as an Object-Oriented Programming project. The system provides separate functionality for customers and administrators, allowing customers to browse food items, add items to a cart, place orders, and view their orders, while administrators can manage food items and update order statuses.

The project demonstrates core Java and OOP concepts together with JavaFX GUI development, exception handling, validation, and file-based data persistence.

---

## ✨ Features

### 👤 Customer Features

* Customer registration
* Customer login
* Browse available food items
* View food details
* Add food items to cart
* Place orders
* View order confirmation
* View previous orders

### 👨‍💼 Admin Features

* Admin login
* Add new food items
* Edit food information
* Update food prices
* Remove food items
* Manage customer orders
* Update order status

### ⚙️ System Features

* JavaFX graphical user interface
* Object-oriented design
* Encapsulation, inheritance, abstraction, and polymorphism
* Input validation
* Custom exception handling
* File-based persistence
* Automatic data loading when the application starts
* Automatic data saving when the application closes

---

## 🏗️ System Architecture

The project follows a separation-of-concerns approach so that different parts of the system have different responsibilities.

```text
                 Urban Bites
                     │
          ┌──────────┴──────────┐
          │                     │
      Customer                 Admin
          │                     │
          └──────────┬──────────┘
                     │
                JavaFX GUI
                     │
              Service / Logic
                     │
                Model Classes
                     │
              File Persistence
                     │
                Text Files
```

### Main Layers

**GUI Layer**

Handles the JavaFX interface and user interaction.

**Model Layer**

Represents the main objects and their data, such as food items, customers, and orders.

**Service Layer**

Handles application logic and operations such as menu and order management.

**Persistence Layer**

Handles saving and loading application data from files.

---

## 🧩 OOP Concepts Used

### Encapsulation

Data fields are kept private and accessed through appropriate getters and setters. This helps control how object data is accessed and modified.

### Inheritance

Food categories are represented using subclasses of `FoodItem`.

```text
FoodItem
├── VegItem
├── NonVegItem
└── BeverageItem
```

The project also uses inheritance for user-related classes such as `Customer` and `Admin`.

### Polymorphism

A general `FoodItem` reference can work with different food item subclasses, allowing common operations to work with different types of food.

### Abstraction

Common properties and behavior are defined at a general level while specific subclasses provide their own implementations where required.

### Interfaces

Interfaces are used as contracts between classes where applicable, including functionality related to persistence.

---

## 👥 Customer Workflow

```text
Register
   ↓
Login
   ↓
Customer Dashboard
   ↓
Public Menu
   ↓
View Food
   ↓
Add to Cart
   ↓
Cart
   ↓
Place Order
   ↓
Confirmation
   ↓
My Orders
```

---

## 👨‍💼 Admin Workflow

```text
Admin Login
     ↓
Admin Dashboard
     ↓
┌───────────────┬────────────────┐
│               │                │
Food Management  Order Management
│               │
├── Add Food     └── Update Order Status
├── Edit Food
├── Update Price
└── Remove Food
```

---

## 💾 File Persistence

Urban Bites uses file-based persistence to ensure that important application data is not lost when the program closes.

The application loads existing data when it starts and saves updated data when it closes.

```text
Application Starts
       ↓
     init()
       ↓
   loadAll()
       ↓
Load saved data
```

When the application closes:

```text
Application Closes
       ↓
     stop()
       ↓
    saveAll()
       ↓
Save updated data
```

The system stores menu, customer, and order information using text-based files.

---

## ⚠️ Validation & Exception Handling

The system includes validation and custom exception handling to prevent invalid operations and provide appropriate feedback.

Examples include:

* Duplicate customer registration
* Duplicate food items
* Food item not found
* Empty order
* Invalid user input
* Invalid numeric values

Custom exceptions include:

```text
DuplicateCustomerException
DuplicateFoodException
FoodNotFoundException
EmptyOrderException
```

---

## 🖥️ Technologies Used

| Technology                      | Purpose                          |
| ------------------------------- | -------------------------------- |
| **Java**                        | Core application development     |
| **JavaFX**                      | Graphical user interface         |
| **Object-Oriented Programming** | Application structure and design |
| **File I/O**                    | Data persistence                 |
| **Eclipse IDE**                 | Development environment          |

---

## 📁 Project Structure

The project is organized into packages based on responsibility.

```text
src/
├── gui/
│   ├── Login
│   ├── CustomerDashboard
│   ├── AdminDashboard
│   ├── PublicMenu
│   ├── FoodMenu
│   └── OrderManagement
│
├── model/
│   ├── FoodItem
│   ├── VegItem
│   ├── NonVegItem
│   ├── BeverageItem
│   ├── Customer
│   ├── Admin
│   └── Order
│
├── service/
│   └── RestaurantSystem
│
└── exception/
    ├── DuplicateCustomerException
    ├── DuplicateFoodException
    ├── FoodNotFoundException
    └── EmptyOrderException
```

> The exact structure may vary depending on the current version of the source code.

---

## 🚀 Getting Started

### Prerequisites

Make sure you have:

* Java JDK installed
* JavaFX SDK installed
* An IDE such as Eclipse or IntelliJ IDEA

### Installation

1. Clone the repository:

```bash
git clone https://github.com/your-username/urban-bites.git
```

2. Open the project in your Java IDE.

3. Configure the JavaFX SDK in your project.

4. Make sure the required JavaFX modules are included in the project configuration.

5. Run the main application class.

---

## 🧪 Testing

The system was tested using normal operations as well as invalid input and edge cases.

Examples include:

| Test                           | Expected Result                |
| ------------------------------ | ------------------------------ |
| Register customer              | Customer account created       |
| Register duplicate customer    | Duplicate customer error       |
| Login with valid credentials   | Dashboard displayed            |
| Login with invalid credentials | Error displayed                |
| Add food item                  | Food appears in menu           |
| Remove food item               | Food removed                   |
| Place an empty order           | `EmptyOrderException`          |
| Search for unavailable food    | `FoodNotFoundException`        |
| Close and reopen application   | Previously saved data restored |

---

## 🔮 Future Improvements

Although Urban Bites provides the core functionality of a food ordering system, several improvements could be added in future versions.

* Move from text-file storage to a relational database such as MySQL or PostgreSQL
* Hash customer and admin passwords
* Add menu search, filtering, and sorting
* Add real online payment processing
* Add support for multiple concurrent users
* Develop a client-server or REST API architecture
* Provide web or mobile clients
* Improve authentication and security

---

## 🎯 Project Objectives

The main objective of Urban Bites is to develop a Java-based food ordering system that allows customers to browse and order food while providing administrators with tools to manage food items and orders.

The project also aims to demonstrate practical application of:

* Object-oriented programming
* JavaFX GUI development
* File I/O and persistence
* Exception handling
* Input validation
* Separation of responsibilities

---

## 👩‍💻 Authors

**Umme Hafsa Mazumder**
**Nanzeeba Ayman**

Object Oriented Programming Project — CSCI2002

---

## 📌 Project Status

**Completed — Academic Project**

Future versions may extend the system with database integration, secure authentication, online payment, and a client-server architecture.

---

## 📄 License

This project was developed for academic purposes.
