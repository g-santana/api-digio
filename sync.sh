#!/bin/sh

HOST=$(echo "$1" | cut -d: -f1)
PORT=$(echo "$1" | cut -d: -f2)
shift
CMD="$@"

echo "Aguardando $HOST:$PORT ficar disponível..."

while ! nc -z "$HOST" "$PORT"; do
  sleep 1
done

echo "$HOST:$PORT está disponível. Executando comando: $CMD"
exec $CMD
