# 🚀 Spring Boot + Redis Cache Application

A simple backend application built with **Spring Boot** and **Redis** for caching, containerized with **Docker** and deployable on **Kubernetes**.

---

## 📌 Features

- Spring Boot REST API
- Redis caching integration
- Dockerized application
- Docker Compose setup (Spring Boot + Redis)
- Kubernetes deployment manifests
- Environment-based configuration

---

## 🧱 Tech Stack

- Java 17+
- Spring Boot 3+
- Redis 7
- Docker & Docker Compose
- Kubernetes
- Maven

---

## Docker Usage 
### Build image 
```docker build -t spring-boot-app:latest .```
### Run container 
```docker run -p 8080:8080 spring-boot-app:latest```

## Kubernetes Deployment
### Apply all manifests
```kubectl apply -f k8s/```

### Check running pods
```kubectl get pods```

## 🚀 Future Improvements 
- Add Redis persistence (PVC in Kubernetes)
- Add Spring Boot Actuator (health checks)
- Add Ingress controller for routing
- Add CI/CD pipeline (GitHub Actions)
- Add Helm charts for production deployment