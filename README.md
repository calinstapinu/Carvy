# Carvy - Drive Your Dream, Own Your Journey! 

## Table of Contents

1. [Description](#description)
2. [Carvy Diagram](#carvy-diagram)

 # Description
**Carvy is an application for a car dealership specializing in car sales and leasing. It allows the management of available car stock, adding new models, marking cars as sold or leased, and storing information about clients and employees. The application’s goal is to streamline the activity of a car dealership and simplify the sales and leasing contract management processes.**

> [!IMPORTANT]
> ## What can Carvy do for you? 

1. ***Managing Client and Employee Information***:
   
   • The application allows **adding, modifying,** and **viewing** information about clients and employees.
   In the data structure, the abstract class **Person** serves as a base for the classes **Client** and **Employee**,
   which contain specific attributes and methods for each category.
   
   • This setup facilitates **efficient management** of information related to the people involved in the
   sales and leasing process.
   
3. ***Tracking Car Details in Stock***:
   
   • The **Car** class manages technical details relevant for each car, such as **brand, model, year of manufacture,
   mileage, price,** and **status** (available, sold, or leased). This way, the dealership can have a **clear picture**
   of its stock and make decisions based on client needs.

   • This tracking is essential for employees to respond quickly to client requests regarding **car sales** or **leasing.**

4. ***Ability to Mark Cars as Sold and Dynamically Update Available Stock***:

   • The feature to mark cars as **sold** or **leased** is included in the **Car** class, allowing the status of each car to be updated.
   Employees can change a **car's status**, and the stock is **dynamically updated,** helping maintain a precise and updated inventory record.

5. ***Managing Leasing Contracts with Rules for Calculating Monthly Rates and Credit Conditions***:

   • The **Leasing** class is responsible for **creating** and **managing** leasing contracts. It includes a **complex method** for calculating the **monthly rate**,
   taking into account the **car's price, contract duration, interest rate**, and **client credit profile.**
   
   - **Monthly Rate Calculation:**
      Based on the contract duration and interest rate, this method adjusts the monthly rate to reflect the car's price, terms, and conditions.
   -  **Rate Adjustment Based on Credit History:**
      If the client has a favorable credit history, the application can adjust the monthly rate, providing more advantageous terms.
   -  **Total Amount Calculation:**
      In addition to the monthly rate, this method calculates the total amount owed at the end of the contract, covering all associated costs.
   -  **Leasing Finalization:**
      A method that finalizes the leasing contract, updating the car's status and linking the client to the leasing contract in the databas.


# Carvy Diagram
 <div class="badges-gif">
  <p align="right">
    <img src="https://i.imgur.com/IRJ6wol.jpeg" width=100% align=center alt=TerraPulse-Demo>
    <br><br>
  </p>
</div>
