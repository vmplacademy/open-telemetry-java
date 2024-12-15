# Mythological Application Monitoring

## Project Purpose

The purpose of this repository is to provide a monitoring solution for a mythological application. The repository includes configurations and code for setting up and running various services and applications.

## Repository Structure

- `docker/docker-compose.yml`: Contains the configuration for running Prometheus and Grafana as Docker containers.
- `docker/prometheus/prometheus.yml`: Defines the global settings and scrape configurations for Prometheus.
- `docker/prometheus/targets_smart-city.json`: Contains the targets for the `smart-city` job, specifying the `smart-mobility` and `payment` services.
- `docker/grafana/config`: Sets the admin user and password for Grafana.
- `payment`: Contains the source code for the Payment application, built using Spring Boot and Kotlin/Java.
- `smartmobility`: Contains the source code for the Smart Mobility application, built using Spring Boot and Java.
- `pom.xml`: Defines the parent project and includes modules for the Payment and Smart Mobility applications.
- `scripts`: Contains various scripts for building, running, and managing the applications and services.

## Prerequisites

Ensure you have Docker and Docker Compose installed on your machine.

## Building and Running the Applications

1. Navigate to the root directory of the repository.
2. Run the `scripts/setup_all.sh` script to build the applications and start the Docker containers. This script will:
   - Stop and remove any existing Docker containers.
   - Build the applications using Maven.
   - Start the Docker containers for Prometheus, Grafana, Smart Mobility, and Payment services.
3. Wait for the Docker containers to start. You can check the status of the containers using the `docker ps` command.
4. Access the applications and services:
   - Prometheus: `http://localhost:9090`
   - Grafana: `http://localhost:3000`
   - Smart Mobility: `http://localhost:8090`
   - Payment: `http://localhost:8091`

## Configuring Prometheus and Grafana

- The `docker/docker-compose.yml` file contains the configuration for running Prometheus and Grafana as Docker containers.
- Prometheus is configured to use the `docker/prometheus/prometheus.yml` file located in the `docker/prometheus/` directory.
- Grafana is configured to use the provisioning files located in the `docker/grafana/provisioning/` directory.
- The Prometheus configuration file, `docker/prometheus/prometheus.yml`, defines the global settings and scrape configurations.
- The `docker/prometheus/targets_smart-city.json` file contains the targets for the `smart-city` job, specifying the `smart-mobility` and `payment` services along with their respective ports and labels.
- The Grafana configuration file, `docker/grafana/config`, sets the admin user and password for Grafana.
- Start the Docker containers using the `docker-compose` command to bring up the Prometheus and Grafana services along with the `smart-mobility` and `payment` services.
