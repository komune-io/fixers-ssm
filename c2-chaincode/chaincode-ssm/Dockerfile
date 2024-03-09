ARG VERSION
ARG VERSION_ALPINE
ARG VERSION_FABRIC

FROM alpine:${VERSION_ALPINE} as FETCHER

WORKDIR /git

RUN apk update && \
	apk upgrade && \
	apk --no-cache add tar=1.35-r2 wget=1.21.4-r0 unzip=6.0-r14

RUN wget --no-check-certificate --progress=dot -O clone.zip https://github.com/apoupard/blockchain-ssm/archive/fabric-v2-images.zip && \
    unzip clone.zip && \
    mv blockchain-ssm-* blockchain-ssm

FROM hyperledger/fabric-tools:${VERSION_FABRIC} as BUILDER

ARG VERSION

WORKDIR /go/src/chaincode-ssm

COPY --from=FETCHER /git/blockchain-ssm/chaincode /go/src/chaincode-ssm/chaincode

RUN go mod init &&\
    go get github.com/golang/protobuf@v1.3.2 &&\
    go get google.golang.org/grpc@v1.23.0 &&\
    go get github.com/hyperledger/fabric-chaincode-go@release-2.3 &&\
    go get github.com/hyperledger/fabric-protos-go@a3136dde23548471516ec3eb360e23985d6ebe45 &&\
    go mod vendor

RUN peer chaincode package -n ssm -p chaincode/go/ssm -v $VERSION ssm-$VERSION.pak

FROM alpine:${VERSION_ALPINE}

ARG VERSION

WORKDIR /opt/chaincode/ssm

LABEL org.opencontainers.image.source https://github.com/komune-io/connect-ssm

RUN echo "CHAINCODE=ssm" > env_ssm \
    && echo VERSION=$VERSION >> env_ssm

COPY --from=FETCHER /git/blockchain-ssm/sdk/cli/util ./bin
COPY --from=FETCHER /git/blockchain-ssm/chaincode ./chaincode
COPY --from=BUILDER /go/src/chaincode-ssm/ssm-$VERSION.pak ./
