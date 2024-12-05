# Luna
Project of an online platform for the sale and distribution of e-books

# App functionalities
* Main Page
![shop-dune.png](readme-images/shop-dune.png)
* Login page
![img_1.png](readme-images/log-in.png)
* Register page
![img_2.png](readme-images/create-acc.png)
* confirmation of user creation, user is inactive until he confirms his email by sent link
![img_3.png](readme-images/registration.png)
* To confirm account email we sent an email, SMT server is simulated in developer environment using Maildev tool
* https://github.com/maildev/maildev
![img_mail.png](readme-images/maildev-acc-creation.png)
* Main page for logged user
![img_main-logged.png](readme-images/main-page-for-logged-user.png)
* Dune page in shop
![img_4.png](readme-images/dune-book-page.png)
* search bar in top bar
![img_5.png](readme-images/dynamic-search-bar.png)
* user can directly click book page showed in search results or enter just keyworld leading him into advanced search page
* advanced search page for typed h, results respond to filtering without need for page reloading  
![img_7.png](readme-images/adv-search.png)
* notification using react toastify after adding book to cart, many notifications like this are used to inform user about actions
![img_7.png](readme-images/notify.png)

# Purchases - PayU
* User cart after adding a couple of books
![img_7.png](readme-images/cart-for-Mike.png)
* buy leads user to external payment operator PayU
![img_7.png](readme-images/PayU-Mike-order.png)
* Mike chooses payment option
![img_7.png](readme-images/payU-Mike-blik.png)
* after payment PayU navigates user back to our app where user can check his order
  ![img_7.png](readme-images/succesful-payment.png)
* books are added to his personal library where he can download them at any time
  ![img_7.png](readme-images/Mike-library.png)
* his cart is cleared after purchase
  ![img_7.png](readme-images/cart-empty-mike.png)
* We can track payments and their status at sandbox provided by PayU, nearly identical system to PayU payment system
  ![img_7.png](readme-images/payU-sanbox-panel.png)
* We can see that payment by Mike has been finished successfully 

# Password recovery
* User can type his email in case of forgetting password
* An otp is generated lasting 5 min for password recover, an email is sent to user
  ![img_7.png](readme-images/Mike-pass-recv-maildev.png)
* typing new password
* ![img_7.png](readme-images/password-recovery.png)
* success
  ![img_7.png](readme-images/password-reset-mike.png)
  
# Frontend
using vite, react, typescript and tailwind on separate repository
https://github.com/KaVerSv/Luna-frontend

# Database
PostgreSQL database in docker container
![img_7.png](readme-images/luna-docker.png)
Diagram of database
![img_7.png](readme-images/data-base.png)

# Technologies
* Docker: Utilized for hosting the PostgreSQL database, ensuring scalability and streamlined containerized deployment.
* Hibernate: Implemented as the Object-Relational Mapping (ORM) tool to facilitate seamless interaction between the Java application and the database.
* Java Spring Security: Employed for authentication and authorization within the project, securing API calls through the use of JWT tokens.