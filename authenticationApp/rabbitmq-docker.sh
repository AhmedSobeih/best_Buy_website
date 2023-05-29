#!/bin/bash

apt-get update && apt-get install -y curl

# Start the RabbitMQ server
rabbitmq-server

# Wait for RabbitMQ to be available
while ! curl -s -o /dev/null http://localhost:15672; do
    echo "retry connection"
    sleep 1
done

echo "RabbitMQ server ready"

# Define the queues
queues=("AuthenticationSender" "AuthenticationReceiver")

# Create the queues using the RabbitMQ Management API
for queue in "${queues[@]}"; do
    curl -i -u guest:guest -H "content-type:application/json" \
         -XPUT -d'{"auto_delete":false,"durable":true}' \
         "http://localhost:15671/api/queues/%2F/AuthenticationReceiver"
done

echo "Queues ready"
