#!/bin/bash

PROJECT_ID=${GKE_PORJECT_ID};

gcloud beta container clusters create istio-demo --project=${PROJECT_ID} \
    --addons=Istio --istio-config=auth=MTLS_PERMISSIVE \
    --cluster-version=latest \
    --machine-type=n1-standard-2 \
    --zone europe-west1-b \
    --num-nodes=2;

export STAR_WARS_PROJECT_HOME="$(dirname $0)/";
kubectl apply -f <(istioctl kube-inject -f ${STAR_WARS_PROJECT_HOME}kubernetes/starwars.yaml);