kubectl get deployments -l app=my-app  # get number of pods for service 
kubectl scale --replicas=<count> <resource_type>/<name> # scale service
