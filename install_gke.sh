#!/bin/bash

PROJECT_ID=${GKE_PORJECT_ID};

# creating k8s cluster on GKE with Istio enabled
gcloud beta container clusters create istio-demo-gke --project=${PROJECT_ID} \
    --addons=Istio \
    --istio-config=auth=MTLS_PERMISSIVE \
    --cluster-version=latest \
    --machine-type=n1-standard-2 \
    --zone europe-west1-b \
    --num-nodes=2;

# deploy application
export STAR_WARS_PROJECT_HOME="$(dirname $0)/";
kubectl apply -f <(istioctl kube-inject -f ${STAR_WARS_PROJECT_HOME}kubernetes/starwars.yaml);

# Istio gateway
kubectl apply -f ${STAR_WARS_PROJECT_HOME}istio-rules/starwars-gateway.yaml;

# wait for a wile so the istiogateway is started
sleep 60;
export GATEWAY_URL=$(kubectl -n istio-system get svc istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].ip}');
echo "Gateway URL: ${GATEWAY_URL}";
