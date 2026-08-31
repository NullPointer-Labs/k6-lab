# K6 Load Testing Lab: Spring Boot vs Quarkus

This repository contains the infrastructure and scripts for the architectural stress laboratory featured on the **Null Pointer** channel. The objective of this environment is to test the scalability limits of the JVM under extreme load, monitor raw memory consumption, and force "Stop the World" behavior in the Garbage Collector.

The lab puts the two giants of the Java ecosystem face-to-face: Spring Boot and Quarkus, testing a complete Transaction API flow.

## Prerequisites

To run this laboratory in your local environment, you will need native tools for Unix/Linux-based systems (like Pop!_OS, used in this project).

*   **Linux OS** (Pop!_OS, Ubuntu, Mint, etc.)
*   **Docker Engine & Docker Compose** (To spin up the API containers and databases)
*   **K6 OSS** (To fire the load tests)
*   **Java 21+** (Only required if you want to compile the projects locally outside Docker)

## Repository Structure

Based on the actual project architecture, the lab is divided into independent modules:

```text
.
├── pointer-quarkus/          # Quarkus API source code
│   ├── src/main/java/...     # Transaction controllers, services, and repos
│   ├── docker-compose.yml    # Quarkus isolated infrastructure
│   └── Dockerfile
├── pointer-spring/           # Spring Boot API source code
│   ├── src/main/java/...     # Transaction controllers, services, and repos
│   ├── docker-compose.yml    # Spring Boot isolated infrastructure
│   └── Dockerfile
└── scripts/                  # K6 stress test progressive scripts
    ├── lvl2.js
    ├── lvl3.js
    ├── lvl4.js
    └── lvl5.js               # Maximum load script
```

## Executing the Lab

Since both frameworks might compete for the same host resources and ports, it is highly recommended to test them one at a time to get accurate benchmarking results.

### 1. Test the Spring Boot API

Navigate to the Spring Boot directory and start its dedicated containerized environment:

```bash
# Navigate to the Spring Boot module
cd pointer-spring

# Build and start the containers in detached mode
docker compose up -d --build

# Verify if the services are running properly
docker compose ps
```

### 2. Test the Quarkus API

When you are ready to test Quarkus, make sure to spin down the Spring Boot environment first, then start Quarkus:

```bash
# Bring down Spring Boot
cd ../pointer-spring
docker compose down -v

# Navigate to the Quarkus module
cd ../pointer-quarkus

# Build and start the containers
docker compose up -d --build
```

### 3. Run the Stress Tests (K6)

The `scripts/` directory contains different levels of stress tests (`lvl2.js` up to `lvl5.js`). To inject maximum sustained memory and CPU pressure against the running API, execute the highest level script from the repository root:

```bash
# Navigate back to the repository root
cd ..

# Execute K6 load test against the target API
k6 run scripts/lvl5.js
```

To monitor your machine's resources and observe "bit scrubbing" during the test, open a new terminal tab and use `htop`:

```bash
htop
```

### 4. Teardown

After finalizing the performance analysis for both frameworks, destroy the active lab and free up your hardware resources.

```bash
# Inside either pointer-spring or pointer-quarkus directory
docker compose down -v
```

## The Challenge

The challenge from the video has been set. Take these progressive K6 scripts (`lvl2` through `lvl5`), point them at your own API, and find out if your current architecture stands the impact or melts under extreme production pressure.

## Author

**Null Pointer**
Commercial Contact: contato.nullpointer@gmail.com
