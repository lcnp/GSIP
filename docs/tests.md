# GSIP tests

## /id

http://localhost:8080/gsip/id/mo/0003847526

## /dat

This should send a 307 to another URL 

curl -I http://localhost:8080/gsip/dat/mo/0003847526/nrcan-rncan/csv

This should 404

curl -I http://localhost:8080/gsip/dat/does-not-exists

