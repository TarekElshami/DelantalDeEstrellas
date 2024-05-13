#!/bin/bash
#mkdir -p $HOME/.postgresql
#wget -O $HOME/.postgresql/root.crt https://cockroachlabs.cloud/clusters/94285693-fa2e-4a98-a3ab-589ceb61e17a/cert
curl --create-dirs -o ~/.postgresql/root.crt -O https://cockroachlabs.cloud/clusters/94285693-fa2e-4a98-a3ab-589ceb61e17a/cert