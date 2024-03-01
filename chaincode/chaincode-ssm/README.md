# SSM Chaincode

This repository provides a Docker configuration to package the blockchain-ssm chaincode, 
specifically designed for Hyperledger Fabric, within a Docker container. 
The Docker image, `komine-io/chaincode-ssm`, is based on Linux Alpine 
and includes all necessary components and scripts for the chaincode's operation 
within a Hyperledger Fabric network.

## Docker Image Structure

The `komine-io/chaincode-ssm` image contains the following key directories and files:

- `/opt/ssm/chaincode/go/ssm/`: Contains the source code for the ssm chaincode, tailored for Hyperledger Fabric.
- `/opt/ssm/util`: Includes bash scripts for chaincode instantiation, invocation, and querying within a Hyperledger Fabric network.
- `/opt/ssm/env`: Contains environment properties essential for the chaincode's operation.
- `/opt/ssm/ssm-$VERSION.pak`: The packaged chaincode file, ready for deployment on Hyperledger Fabric.

### Environment Configuration

The environment file `/opt/civis-blockchain/ssm/env` includes the following properties:
CHAINCODE=ssm
VERSION=0.8.1

## Build Process`

### Packaging and Pushing Docker Images

1. Package, tag as the latest version, and push Docker images:

```bash
export VERSION=$(cat VERSION)
export DOCKER_REPOSITORY=ghcr.io/
make chaincode-ssm-package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY}
```

## Docker Image Operations

- **Inspect Docker Image's Content:**

```bash
export VERSION=$(cat VERSION)
make chaincode-ssm-inspect -e VERSION=${VERSION}
```