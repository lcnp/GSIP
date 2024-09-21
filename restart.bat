podman kill gs
podman build -t gsip .
podman run -d --rm -p 8080:8080 --env-file erml.env --name gs gsip
