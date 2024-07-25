# https://blog.sebastian-daschner.com/entries/init-databases-quarkus

docker run -d --rm \
  --name data-driven-dinner \
  -p 5432:5432 \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  postgres:15
  # -v /resources/scripts:/scripts:ro \




