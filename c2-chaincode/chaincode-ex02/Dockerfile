ARG VERSION
ARG VERSION_ALPINE
ARG VERSION_FABRIC

FROM hyperledger/fabric-tools:${VERSION_FABRIC} as BUILDER

ARG VERSION

WORKDIR /go/src/chaincode-ex02

COPY ./chaincode ./chaincode

RUN go mod init &&\
    go get github.com/golang/protobuf@v1.3.2 &&\
    go get google.golang.org/grpc@v1.23.0 &&\
    go get github.com/hyperledger/fabric-chaincode-go@release-2.3 &&\
    go get github.com/hyperledger/fabric-protos-go@a3136dde23548471516ec3eb360e23985d6ebe45 &&\
    go mod vendor

RUN peer chaincode package -n ex02 -p chaincode/go/ex02 -v ${VERSION} ex02-${VERSION}.pak

FROM alpine:${VERSION_ALPINE}

ARG VERSION

WORKDIR /opt/chaincode/ex02

LABEL org.opencontainers.image.source https://github.com/komune-io/connect-ssm

RUN echo "CHAINCODE=ex02" > env_ex02 \
    && echo VERSION=${VERSION} >> env_ex02

COPY ./chaincode ./chaincode

COPY --from=BUILDER /go/src/chaincode-ex02/ex02-${VERSION}.pak ./
