#!/bin/bash

# starting local k8s cluster (minikube)
minikube start --memory=8192 --cpus=4 --kubernetes-version=v1.10.0 \
    --extra-config=controller-manager.cluster-signing-cert-file="/var/lib/localkube/certs/ca.crt" \
    --extra-config=controller-manager.cluster-signing-key-file="/var/lib/localkube/certs/ca.key";

#installing star wars project with
export STAR_WARS_PROJECT_HOME="$(dirname $0)/";
kubectl apply -f ${STAR_WARS_PROJECT_HOME}kubernetes/starwars.yaml;
