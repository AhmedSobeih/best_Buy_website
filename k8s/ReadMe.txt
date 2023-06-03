kubectl get deployments -l app=my-app  # get number of pods for service 
kubectl scale --replicas=<count> <resource_type>/<name> # scale service


-install nginx
-kubectl apply ingress.yaml
-minikube addons enable ingress
-minikube ip (get ip  address)
