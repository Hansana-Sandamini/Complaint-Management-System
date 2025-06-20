# Web-Based Complaint Management System using JSP

## 📌 Project Overview

This project is a Web-Based Complaint Management System (CMS) developed as part of the IJSE Graduate Diploma in Software Engineering (GDSE) - Advanced API Development module. The system is designed for the Municipal IT Division to enable employees to submit, view, edit, and delete complaints, and for admins to manage all complaints, update their status, and add remarks. The application strictly adheres to synchronous HTTP form submissions (GET/POST) using JavaServer Pages (JSP), Jakarta EE, and MySQL, following the Model-View-Controller (MVC) architecture. Asynchronous mechanisms like AJAX are not used, per the assignment constraints.

---

## 🎥 Demo Video
Check out the system in action on YouTube: https://www.youtube.com/watch?v=mId8LDF_RF8

---

![Screenshot from 2025-06-20 22-18-13](https://github.com/user-attachments/assets/86ee633d-4fcc-47ac-8685-dda2ade67668)

![Screenshot from 2025-06-20 22-25-18](https://github.com/user-attachments/assets/0ca5724f-c440-4a58-87e1-3b8acd5b1fa5)

![Screenshot from 2025-06-20 22-23-05](https://github.com/user-attachments/assets/1225c828-2482-4880-9a02-8fc9f0ad2cdd)

---

## ✨ Features
- **Authentication Module**: Login with session management and role-based access control.
- **Complaint Management**:
  - **Employees**: Submit, view, edit, and delete unresolved complaints.
  - **Admins**: View all complaints, update status/remarks, and delete complaints.

---

## 🛠️ Technologies Used

| Layer        | Technology                     |
|--------------|--------------------------------|
| Frontend     | JSP, HTML, CSS, JavaScript (only for validation) |
| Backend      | Jakarta EE (Servlets), Apache Commons DBCP |
| Database     | MySQL, accessed via DBCP connection pooling |
| Deployment   | Apache Tomcat (local environment) |

---

## 🚀 System Architecture
- **MVC Pattern**:
  - **View**: JSP pages for UI rendering.
  - **Controller**: Servlets handling HTTP requests/responses.
  - **Model**: POJOs (JavaBeans) and DAO classes for business logic/database operations.

---

## 📁 Project Structure

<pre>
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── lk/ijse/aad/cms/
│   │   │       ├── controller/        # Servlets (SignInServlet, ComplaintManagementServlet etc.)
│   │   │       ├── dto/               # Data Transfer Objects (ComplaintDTO, UserDTO)
│   │   │       └── model/             # Business Logic Layer (ComplaintModel, UserModel)
│   │   ├── db/                        # SQL Schema and DB related files
│   │   │   └── schema.sql
│   │   └── resources/                 # Config or static resource files
│
├── webapp/
│   ├── assets/                        # Image assets
│   ├── css/                           # Stylesheets for UI (dashboard, complaint, etc.)
│   ├── pages/                         # JSP Views (signin.jsp, signup.jsp, dashboards, etc.)
│   ├── web/
│   │   └── META-INF/      
│             └── context.xml          # Context configuration
│       └── WEB-INF/
│             └── web.xml              # Deployment descriptor
│
├── .idea/, .mvn/, out/                # IDE and build-related directories (auto-generated)
└── README.md
</pre>

---

## ⚙️ Setup & Configuration

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Hansana-Sandamini/Complaint-Management-System.git
