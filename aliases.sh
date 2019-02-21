# aliases for telemetry services
alias jpf='kubectl port-forward -n istio-system $(kubectl get pod -n istio-system -l app=jaeger -o jsonpath="{.items[0].metadata.name}") 16686:16686';
alias ppf='kubectl -n istio-system port-forward $(kubectl -n istio-system get pod -l app=prometheus -o jsonpath="{.items[0].metadata.name}") 9090:9090';
alias gpf='kubectl -n istio-system port-forward $(kubectl -n istio-system get pod -l app=grafana -o jsonpath="{.items[0].metadata.name}") 3000:3000';
alias sgpf='kubectl -n istio-system port-forward $(kubectl -n istio-system get pod -l app=servicegraph -o jsonpath="{.items[0].metadata.name}") 8088:8088';
alias kpf='kubectl -n istio-system port-forward $(kubectl -n istio-system get pod -l app=kiali -o jsonpath="{.items[0].metadata.name}") 20001:20001';
