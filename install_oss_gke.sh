#!/bin/bash

PROJECT_ID=${GKE_PORJECT_ID};

# creating k8s cluster on GKE
gcloud container clusters create istio-demo --project=${PROJECT_ID} \
    --machine-type=n1-standard-2 \
    --num-nodes=2 \
    --no-enable-legacy-authorization;

# add permissions to the GKE user
kubectl create clusterrolebinding cluster-admin-binding \
  --clusterrole=cluster-admin \
  --user="$(gcloud config get-value core/account)";

# installing istio on k8s cluster
export ISTIO_HOME="$(dirname ${ISTIO_PATH})/";
kubectl apply -f ${ISTIO_HOME}/install/kubernetes/helm/istio/templates/crds.yaml;
kubectl apply -f ${ISTIO_HOME}/install/kubernetes/istio-demo.yaml;

# deploy application
export STAR_WARS_PROJECT_HOME="$(dirname $0)/";
kubectl apply -f <(istioctl kube-inject -f ${STAR_WARS_PROJECT_HOME}kubernetes/starwars.yaml);

# Istio gateway
kubectl apply -f ${STAR_WARS_PROJECT_HOME}istio-rules/starwars-gateway.yaml;

# wait for a wile so the istiogateway is started
sleep 60;
export GATEWAY_URL=$(kubectl -n istio-system get svc istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].ip}');
echo "Gateway URL: ${GATEWAY_URL}";
