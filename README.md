# 🛠️ Sistema de Ticketing para Soporte Técnico

> Aplicación fullstack moderna construida con Spring Boot, Thymeleaf y MySQL. Diseñada para gestionar tickets de soporte técnico en instituciones educativas privadas de forma rápida, segura y escalable.
> Ver video demo del software <a href="https://www.youtube.com/watch?v=6yyRfdJnmMk&t=7s">Ver video demo</a>

---

## 📚 Tabla de Contenidos

- [🧠 Descripción del Proyecto](#-descripción-del-proyecto)
- [🛠️ Tecnologías Utilizadas](#️-tecnologías-utilizadas)
- [🧩 Arquitectura del Software](#-arquitectura-del-software)
- [🔑 Funcionalidades Principales](#-funcionalidades-principales)
- [⚙️ Requisitos del Sistema](#️-requisitos-del-sistema)
- [🚀 Instalación Local](#-instalación-local)

---

## 🧠 Descripción del Proyecto

Este sistema permite a **instituciones educativas privadas** registrar y resolver solicitudes técnicas a través de un sistema de tickets. Cada usuario tiene un rol definido y permisos exclusivos:

- 📩 Clientes: pueden registrar sus problemas técnicos.
- 🛠️ Técnicos: reciben tickets y los solucionan o clasifican.
- 🧑‍💼 Administradores: gestionan el sistema, asignan tareas y supervisan.

Está pensado para ser modular, seguro y escalable.

---

## 🛠️ Tecnologías Utilizadas

- ☕ **Spring Boot** (MVC, Security, JPA)
- 🌐 **Thymeleaf** (Frontend HTML dinámico)
- 🐬 **MySQL** (Gestión de base de datos)
- 🎨 **Bootstrap** (Diseño responsivo)
- 🧱 HTML, CSS, JS

---

## 🧩 Arquitectura del Software


La comunicación entre capas se realiza bajo el patrón **MVC (Modelo-Vista-Controlador)**, asegurando la separación de responsabilidades.

---

## 🔑 Funcionalidades Principales

### 👤 ROLE_CLIENTE

- [x] Crear cuenta y loguearse
- [x] Registrar tickets
- [x] Ver y ordenar sus tickets

### 🛠️ ROLE_TECNICO

- [x] Recibir tickets asignados
- [x] Marcar ticket como:
  - ✅ Solucionado
  - ❌ Rechazado
  - 🕒 En análisis
- [x] Generar informe técnico
- [x] Cambiar código de seguridad
- [x] Ordenar tickets

### 🧑‍💼 ROLE_ADMINISTRADOR

- [x] Ver historial de tickets resueltos
- [x] Asignar tickets a técnicos
- [x] CRUD completo de técnicos
- [x] Añadir nuevos dispositivos
- [x] Ver categorías de dispositivos
- [x] Ingreso exclusivo mediante login

---

## ⚙️ Requisitos del Sistema

- ☕ Java 17 o superior
- 🐬 MySQL (local o XAMPP)
- 📦 Spring Boot con dependencias necesarias
- 🌐 Navegador moderno actualizado

---

## 🚀 Instalación Local

### Paso 1: Clonar el repositorio

```bash
git clone [https://github.com/JesusCortezCr/Proyecto-IntegradorI-HuntTech]
💡 Lo que Aprendí
Este proyecto me permitió conocer el ciclo completo de desarrollo de software:

✅ Análisis de requerimientos funcionales y técnicos

🧠 Diseño de base de datos relacional

🔐 Seguridad con roles y control de accesos

🛠️ Implementación MVC con Spring Boot

🧑‍🎨 Interfaz de usuario con Thymeleaf + Bootstrap

Es una experiencia completa que me dio una visión real del desarrollo de software profesional.
