# Using podman

To test GSIP on your desktop (podman running on WSL2)

/!\ : this will work in local mode (static dataset loaded in the image in folder /repos/erml)
dataset available from  https://github.com/gsc-digital-geoscience/geoconx-erml-gsc/tree/main/geoconx_registry

NOT in this workspace to avoid large dataset in the image. must copy file from above url (only the ttl) into repos/erml (or adjust erml.env accordingly) BEFORE building the image

TODO: sharing a volume might make more sense


```console
podman build -t gsip .
podman run -d --rm -p 8080:8080 --env-file erml.env --name gs gsip
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

# rootlessport

rootlessport is not terminated properly

see https://devops.stackexchange.com/questions/17852/what-is-this-rootlessport-and-how-to-get-it-to-release-the-port-hold

```console
ps aux | grep rootlessport 
```

will give you the list of hungup process

``` console
eboisver@L-STF-A130028:~/GSIP$ ps aux | grep rootlessport
eboisver    3615  0.0  0.5 1413244 48072 ?       Sl   04:20   0:01 containers-rootlessport
eboisver    3623  0.0  0.6 1339768 49868 ?       Sl   04:20   0:00 containers-rootlessport-child
eboisver   13561  0.0  0.6 1413500 48776 ?       Sl   04:54   0:00 containers-rootlessport
eboisver   13569  0.0  0.5 1266036 47144 ?       Sl   04:54   0:00 containers-rootlessport-child
eboisver   20982  0.0  0.0   4032  1960 pts/0    S+   05:49   0:00 grep --color=auto rootlessport
```

just kill the `containers-rootlessport` (the child will also be killed)

eg: 

```console 
kill -9 3615
```



