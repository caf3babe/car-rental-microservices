#!/usr/bin/env sh

kubectl apply -f https://github.com/jetstack/cert-manager/releases/download/v1.2.0/cert-manager.crds.yaml
helm repo add jetstack https://charts.jetstack.io
helm repo update
helm install \
    cert-manager jetstack/cert-manager \
    --namespace cert-manager \
    --create-namespace \
    --version v1.2.0
kubectl get pods --namespace cert-manager

cat <<EOF | kubectl apply -f -
apiVersion: cert-manager.io/v1
kind: ClusterIssuer
metadata:
  name: letsencrypt-staging
spec:
  acme:
    # The ACME server URL
    server: https://acme-staging-v02.api.letsencrypt.org/directory
    # Email address used for ACME registration
    email: starv0411@gmail.com
    # Name of a secret used to store the ACME account private key
    privateKeySecretRef:
      name: letsencrypt-staging
    # Enable the HTTP-01 challenge provider
    solvers:
    - http01:
        ingress:
            class: ingress-gce

EOF

kubectl describe clusterissuer letsencrypt-staging

cat <<EOF | kubectl apply -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: main-ingress
  annotations:
    kubernetes.io/ingress.global-static-ip-name: car-rental-v2
    cert-manager.io/cluster-issuer: letsencrypt-staging
    acme.cert-manager.io/http01-edit-in-place: "true"
  labels:
    app: car-rental-v2
spec:
  tls:
  - hosts:
    - v2.se.transfer-vienna.com
    secretName: car-rental-v2-secret
  rules:
  - host: v2.se.transfer-vienna.com
#    http:
#      paths:
#      - path: api/*
#        backend:
#          serviceName: api-gateway
#          servicePort: 8080
#      - path: prometheus/*
#        backend:
#          serviceName: prometheus
#          servicePort: 8080
EOF
