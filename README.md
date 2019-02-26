# starwars-istio
[Istio service mesh](https://istio.io) in practice

## Running starwars-istio example
You have a few options of running the starwars-istio sample apllication.
- Running localy with `minikube`:
  ```bash
  ./install_local.sh
  ```
  
- Running on GKE with Istio addon:
  ```bash
  ./install_gke.sh
  ```
  
- Running on GKE with OSS Istio:
  ```bash
  ./install_oss_gke.sh
  ```
  
## Applying Istio destination rules
### Unencrypted service-to-service traffic
- Apply the `DestinationRule` for the deployed services:
  ```bash
  istioctl create -f istio-rules/destination-rules.yaml
  ```
### Mutual TLS service-to-service traffic
- Apply the `Policy` to allowing only mTLS traffic inside the cluster. (Optional)
  ```bash
  istioctl create -f istio-rules/mtls-strict.yaml
  ```
- Apply the `DestinationRule` for the deployed services:
  ```bash
  istioctl create -f istio-rules/destination-rules-mtls.yaml
  ```
  
## Applying Istio rules
The following section describes the rules in `/istio-rules` folder.
You can apply and delete each rules by running 
`istionctl create -f /istio-rules/<filename>` and 
`istionctl delete -f /istio-rules/<filename>` respectively.

- `traffic-mirroring.yaml` - mirror traffic from `quote-service` and `death-star-service` of "v1" to "v2"
- `fault-injection-delay-images.yaml` - inject the 7s delay to the `image-service` for 100% of the requests
- `fault-injection-error-quote.yaml` - inject HTTP error 500 to the `quote-service` for 100% of the requests
- `request-routing-all-v1.yaml` - route all the requests for `quote-service` and `death-star-service` to version "v1"
- `request-routing-all-v2.yaml` - route all the requests for `quote-service` and `death-star-service` to version "v2"
- `request-routing-chrome-v2.yaml` - route the requests for `quote-service` and `death-star-service` made for browser Google Chrome to version "v2" all other requests are routed to version "1.0"
- `request-timeout.yaml` - route all the request for `quote-service` to "v2" and all the request for `death-star-service` to "v2". 
Sets the 5s timeout to `death-star-service` for requests made from iPad Android iPhone. (Use with `fault-injection-delay-images.yaml` to notice the effect).

