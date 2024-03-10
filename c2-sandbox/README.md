# Network configuration: bclan-it

This setup provides a Hyperledger Fabric Network pre-configured with generated cryptographic materials 
and the SSM chaincode installed, facilitating a quick start for development and testing purposes.

## Usage

To use this pre-configured network, follow these steps:

1. **Copy the Docker Compose File:**

   Copy the `docker-compose-it.yaml` file from this directory to your project:

   ```bash
   cp docker-compose-c2-sandbox.yaml your-project-directory/docker-compose-c2-sandbox.yaml
   ```

2. **Start the Network:**

   Launch the network using Docker Compose:

   ```bash
   docker-compose -f docker-compose-c2-sandbox.yaml up -d
   ```

3. **Copy Configuration:**

   Extract the configuration files from the running container:

   ```bash
   mkdir -p infra
   docker cp cli-init-bclan-network-it:/opt/c2-sandbox/ ./infra/dev
   ```

## Configuration Details

The network's configuration and user credentials are structured as follows:

- **Fabric Configuration:** Located under `/opt/c2-sandbox/fabric`, containing the network configuration files.
- **SSM User Crypto:** User cryptographic materials are stored under `/opt/c2-sandbox/user` with subdirectories for each user and their corresponding public keys.
- **Utilities:** The `util` directory contains scripts to start the `mobilite.eco` API.

You can inspect the configuration using the following command:

```bash
docker run -it komune-io/c2-sandbox-cli:latest /bin/bash
find /opt/c2-sandbox/
```

The output will show the directory structure with configurations for Fabric, users, and utilities.

## Release Process

To package and push the Docker image:

```bash
export VERSION=$(cat VERSION)
export DOCKER_REPOSITORY=ghcr.io/
make c2-sandbox-package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY}
```

