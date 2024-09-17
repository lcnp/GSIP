# Using podman

To test GSIP on your desktop (podman running on WSL2)

/!\ : this will work in local mode (static dataset loaded in the image in folder /repos/erml)
dataset available from  https://github.com/gsc-digital-geoscience/geoconx-erml-gsc/tree/main/geoconx_registry

NOT in this workspace to avoid large dataset in the image. must copy file from above url (only the ttl) into repos/erml (or adjust erml.env accordingly) BEFORE building the image

TODO: sharing a volume might make more sense


```console
podman build -t gsip .
podman run -d --rm -p 8081:8080 --env-file erml.env --name gs gsip
```


if a container seems to be stucked (happens sometime is WSL when windows is shut down with a running container)

`"Error: creating container storage: the container name "gs" is already in use by ..."`

https://github.com/containers/podman/issues/19491#issuecomment-1668129950


```console
podman ps --external
user@device:~/GSIP$ podman ps --external
CONTAINER ID  IMAGE                  COMMAND     CREATED       STATUS      PORTS       NAMES
3af1f442c8fe  localhost/gsip:latest  storage     2 months ago  Storage                 gs
461519d358e2  localhost/gsip:latest  storage     8 days ago    Storage                 gs2
```
will list ids

```console
podman rm --storage --force <CONTAINER ID>
``` 

(name would also work I guess)

if error (invalid argument to unmount overlay/*/merged)

rmdir the folder, then 

```console
podman rm --force <CONTAINER_NAME>
```