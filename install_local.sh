#!/bin/bash

# starting local k8s cluster (minikube)
minikube start --memory=8192 --cpus=4 --kubernetes-version=v1.10.0 \
    --extra-config=controller-manager.cluster-signing-cert-file="/var/lib/localkube/certs/ca.crt" \
    --extra-config=controller-manager.cluster-signing-key-file="/var/lib/localkube/certs/ca.key";

# installing istio on local k8s cluster
export ISTIO_HOME="$(dirname ${ISTIO_PATH})/";
kubectl apply -f ${ISTIO_HOME}/install/kubernetes/helm/istio/templates/crds.yaml;
kubectl apply -f ${ISTIO_HOME}/install/kubernetes/istio-demo.yaml;

#installing star wars project with
export STAR_WARS_PROJECT_HOME="$(dirname $0)/";
kubectl apply -f <(istioctl kube-inject -f ${STAR_WARS_PROJECT_HOME}kubernetes/starwars.yaml);
kubectl apply -f ${STAR_WARS_PROJECT_HOME}istio-rules/starwars-gateway.yaml;
#istioctl create -f ${STAR_WARS_PROJECT_HOME}istio-rules/destination-rules.yaml;

export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}');
export SECURE_INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="https")].nodePort}');
export INGRESS_HOST=$(minikube ip);
export GATEWAY_URL=${INGRESS_HOST}:${INGRESS_PORT};

echo "Gateway URL: ${GATEWAY_URL}";
